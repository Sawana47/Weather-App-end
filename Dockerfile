# -------------------------------
# Stage 1: Build the Spring Boot application
# -------------------------------
FROM maven:3.9.3-eclipse-temurin-8 AS builder

# Set working directory
WORKDIR /app

# Copy Maven build files
COPY pom.xml .

# Copy source code
COPY src ./src

# Build Spring Boot app, skip tests for faster build
RUN mvn clean package -DskipTests

# -------------------------------
# Stage 2: Runtime image (JRE only)
# -------------------------------
FROM adoptopenjdk:8-jre-hotspot

# Set working directory
WORKDIR /app

# Copy the exec
