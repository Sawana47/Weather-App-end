# ------------------------------
# Stage 1: Build the Spring Boot app
# ------------------------------
FROM maven:3.8.8-openjdk-8 AS build

WORKDIR /app

# Copy pom.xml and download dependencies first (cache layer)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire project
COPY src ./src

# Build the project (skip tests to speed up)
RUN mvn clean package -DskipTests

# ------------------------------
# Stage 2: Run the Spring Boot app
# ------------------------------
FROM adoptopenjdk:8-jre-hotspot

WORKDIR /app

# Copy the final JAR from the builder image
COPY --from=build /app/target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Expose local dev port (Render will override with $PORT)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
