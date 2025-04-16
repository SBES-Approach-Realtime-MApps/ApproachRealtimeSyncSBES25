#!/bin/bash
set -e

echo ">> Corrigindo permissões do keyFile"
chmod 600 /data/configdb/mongo-keyfile
chown mongodb:mongodb /data/configdb/mongo-keyfile

echo ">> Iniciando mongod sem autenticação para configuração inicial"
mongod --replSet rs0 --bind_ip_all --fork --logpath /var/log/mongodb.log --logappend --noauth

# Aguarda mongod subir
echo ">> Aguardando MongoDB subir..."
until mongosh --quiet --eval "db.runCommand({ ping: 1 }).ok" | grep -q "1"; do
    echo "Ainda aguardando..."
    sleep 2
done

echo ">> Inicializando Replica Set"
mongosh <<EOF
rs.initiate({
  _id: "rs0",
  members: [{ _id: 0, host: "mongodb:27017" }]
})
EOF

echo ">> Criando usuário root"
mongosh <<EOF
use admin
db.createUser({
  user: "${MONGO_INITDB_ROOT_USERNAME:-admin}",
  pwd: "${MONGO_INITDB_ROOT_PASSWORD:-admin123}",
  roles: [{ role: "root", db: "admin" }]
})
EOF

echo ">> Parando mongod temporário"
mongosh admin <<EOF
db.shutdownServer()
EOF

echo ">> Reiniciando mongod com autenticação"
exec mongod --replSet rs0 --auth --keyFile /data/configdb/mongo-keyfile --bind_ip_all --logpath /var/log/mongodb.log --logappend
