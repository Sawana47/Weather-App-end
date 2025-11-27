# ------------------------------
# Stage 1: Build Spring Boot app
# ------------------------------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Pre-download dependencies (speed up builds)
RUN mvn dependency:go-offline

# Build the application (skip tests)
RUN mvn clean package -DskipTests


# ------------------------------
# Stage 2: Run the Spring Boot app
# ------------------------------
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# App uses port 8080 (Render autodetects)
EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]
