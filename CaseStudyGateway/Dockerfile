FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o Gateway
COPY CaseStudyGateway /app/CaseStudyGateway

# Builda o MS que depende do model
WORKDIR /app/CaseStudyGateway
RUN mvn clean package -DskipTests

# Imagem final para rodar a aplicação
FROM openjdk:21

WORKDIR /app

EXPOSE 8085

COPY --from=build /app/CaseStudyGateway/target/CaseStudyGateway-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]