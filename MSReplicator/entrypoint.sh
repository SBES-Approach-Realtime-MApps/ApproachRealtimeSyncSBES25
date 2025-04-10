#!/bin/sh

# Função para verificar se um conector existe
conector_existe() {
  curl -s http://debezium:8083/connectors/$1 | grep "$1" > /dev/null
}

# Espera Kafka Connect estar pronto
echo "Aguardando Kafka Connect..."
until curl -s http://debezium:8083/connectors > /dev/null; do
  sleep 2
done

# Cria o conector do MongoDB, se não existir
if conector_existe "mongo-connector"; then
  echo "Conector MongoDB já existe, pulando criação..."
else
  echo "Criando conector MongoDB..."
  curl -X POST http://debezium:8083/connectors \
       -H "Content-Type: application/json" \
       -d @/app/debeziumMongo.json
fi

# Cria o conector do PostgreSQL, se não existir
if conector_existe "postgres-connector"; then
  echo "Conector PostgreSQL já existe, pulando criação..."
else
  echo "Criando conector PostgreSQL..."
  curl -X POST http://debezium:8083/connectors \
       -H "Content-Type: application/json" \
       -d @/app/debeziumPostgres.json
fi

# Inicia a aplicação Spring
echo "Iniciando aplicação Spring..."
exec java -jar app.jar