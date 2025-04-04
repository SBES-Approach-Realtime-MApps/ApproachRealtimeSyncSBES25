#!/bin/bash

echo "Iniciando o MongoDB temporariamente..."
mongod --dbpath /data/db --logpath /var/log/mongodb.log --fork --bind_ip_all

# Aguarda o MongoDB iniciar completamente
echo "Aguardando MongoDB iniciar..."
until mongosh --eval "db.runCommand({ping:1})" --quiet; do
  echo "MongoDB ainda não está pronto, aguardando..."
  sleep 3
done

echo "Verificando se usuário admin já existe..."
USER_EXISTS=$(mongosh --quiet --eval "db.getUser('$MONGO_INITDB_ROOT_USERNAME') !== null")

if [ "$USER_EXISTS" == "false" ]; then
  echo "Criando usuário admin..."
  mongosh <<EOF
use admin
db.createUser({
  user: "$MONGO_INITDB_ROOT_USERNAME",
  pwd: "$MONGO_INITDB_ROOT_PASSWORD",
  roles: [{ role: "root", db: "admin" }]
})
EOF
else
  echo "Usuário já existe. Pulando criação."
fi

echo "Desligando MongoDB temporário..."
mongod --shutdown

echo "Iniciando o MongoDB com Replica Set..."
mongod --replSet rs0 --keyFile /data/configdb/mongo-keyfile --bind_ip_all &

# Aguarda o MongoDB iniciar completamente
echo "Aguardando MongoDB iniciar..."
until mongosh --eval "db.runCommand({ping:1})" --quiet; do
  echo "MongoDB ainda não está pronto, aguardando..."
  sleep 3
done

echo "Verificando o estado do Replica Set..."
mongosh --eval "rs.status()" --quiet > /dev/null 2>&1
if [ $? -ne 0 ]; then
  echo "Iniciando o Replica Set..."
  mongosh --eval "rs.initiate({_id: 'rs0', members: [{_id: 0, host: 'mongodb:27017'}]})"
else
  echo "Replica Set já configurado."
fi

# Mantém o MongoDB rodando
wait