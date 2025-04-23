FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o microserviço e o modelo necessário
COPY MSReservation /app/MSReservation
COPY ReservationModel /app/ReservationModel

WORKDIR /app/ReservationModel
RUN mvn clean install -DskipTests

WORKDIR /app/MSReservation
RUN mvn clean package -DskipTests

# Imagem final para rodar a aplicação
FROM openjdk:21

# Diretório de trabalho
WORKDIR /app

# Executável da aplicação
COPY --from=build /app/MSReservation/target/MSReservation-0.0.1-SNAPSHOT.jar app.jar

# Porta da aplicação
EXPOSE 8082

# Comando para executar a aplicação
ENTRYPOINT [ "java", "-jar", "app.jar" ]