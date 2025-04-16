# Imagem do java
FROM openjdk:21

# Diretório de trabalho
WORKDIR /app

# Executável da aplicação
COPY target/MSReplicator-0.0.1-SNAPSHOT.jar app.jar

# Script de inicialização
COPY entrypoint.sh entrypoint.sh

# Arquivos de configuração
COPY debeziumMongo.json debeziumMongo.json
COPY debeziumPostgres.json debeziumPostgres.json

# Porta da aplicação
EXPOSE 8084

# Comando para executar a aplicação
ENTRYPOINT ["sh", "entrypoint.sh"]