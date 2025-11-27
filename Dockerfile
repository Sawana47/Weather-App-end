# ------------------------------
# Stage 1: Build Spring Boot app
# ------------------------------
FROM maven:3.8.6-eclipse-temurin-8 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests


# ------------------------------
# Stage 2: Run the Spring Boot app
# ------------------------------
FROM eclipse-temurin:8-jre

WORKDIR /app

COPY --from=build /app/target/weather-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
