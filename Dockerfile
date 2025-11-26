# -------------------------------
# Stage 1: Build
# -------------------------------
FROM maven:3.8.8-openjdk-8 AS build

WORKDIR /app

# Copy pom.xml first for dependency caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build the Spring Boot app (skip tests)
RUN mvn clean package -DskipTests

# -------------------------------
# Stage 2: Runtime
# -------------------------------
FROM adoptopenjdk:8-jre-hotspot

WORKDIR /app

# Copy the executable JAR from the builder stage
COPY --from=build /app/target/weather-app-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render overrides $PORT)
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
