# Imagem do java
FROM openjdk:21-jdk-alpine

# Diretório de trabalho
WORKDIR /app

# Executável da aplicação
COPY target/MSReplicator-0.0.1-SNAPSHOT.jar app.jar

# Porta da aplicação
EXPOSE 8084

# Comando para executar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]