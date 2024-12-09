# Use a lightweight base image for Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set a working directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/user-service-1.0.0.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
