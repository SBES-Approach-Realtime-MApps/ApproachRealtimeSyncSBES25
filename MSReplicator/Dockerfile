FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o microserviço e o modelo necessário
COPY MSReplicator /app/MSReplicator
COPY CondominiumModel /app/CondominiumModel
COPY ReservationModel /app/ReservationModel
COPY ResidentModel /app/ResidentModel

# Instala todos os models
WORKDIR /app/CondominiumModel
RUN mvn clean install -DskipTests

WORKDIR /app/ReservationModel
RUN mvn clean install -DskipTests

WORKDIR /app/ResidentModel
RUN mvn clean install -DskipTests

# Builda o microserviço
WORKDIR /app/MSReplicator
RUN mvn clean package -DskipTests

# Imagem final para rodar a aplicação
FROM openjdk:21

# Diretório de trabalho
WORKDIR /app

# Executável da aplicação
COPY --from=build /app/MSReplicator/target/MSReplicator-0.0.1-SNAPSHOT.jar app.jar

# Script de inicialização
COPY MSReplicator/entrypoint.sh entrypoint.sh

# Arquivos de configuração
COPY MSReplicator/debeziumMongo.json debeziumMongo.json
COPY MSReplicator/debeziumPostgres.json debeziumPostgres.json

# Porta da aplicação
EXPOSE 8084

# Comando para executar a aplicação
ENTRYPOINT ["sh", "entrypoint.sh"]