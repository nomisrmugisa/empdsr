# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage - Use the official Eclipse Temurin image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built JAR and startup script
COPY --from=build /app/target/*.jar app.jar
COPY start.sh /app/start.sh

# Create directories for H2 database and make script executable
RUN mkdir -p /app/data /app/slave /app/attachments /app/csv && \
    chmod +x /app/start.sh

# Render.com uses PORT environment variable
EXPOSE $PORT
ENTRYPOINT ["/app/start.sh"]