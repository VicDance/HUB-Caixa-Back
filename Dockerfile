FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
COPY --from=build /app/target/HUB-Caixa-Back-1.0-SNAPSHOT.jar app.jar

# RUN mkdir -p /app/data

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
