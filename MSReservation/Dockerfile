# Imagem do java
FROM openjdk:21

# Diretório de trabalho
WORKDIR /app

# Executável da aplicação
COPY target/MSReservation-0.0.1-SNAPSHOT.jar app.jar

# Porta da aplicação
EXPOSE 8082

# Comando para executar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]