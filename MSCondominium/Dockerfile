FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o microserviço e o modelo necessário
COPY MSCondominium /app/MSCondominium
COPY CondominiumModel /app/CondominiumModel

# Instala o model localmente
WORKDIR /app/CondominiumModel
RUN mvn clean install -DskipTests

# Builda o MS que depende do model
WORKDIR /app/MSCondominium
RUN mvn clean package -DskipTests

# Imagem final para rodar a aplicação
FROM openjdk:21

WORKDIR /app

EXPOSE 8080

# Copia o jar do MSCondominium
COPY --from=build /app/MSCondominium/target/MSCondominium-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
