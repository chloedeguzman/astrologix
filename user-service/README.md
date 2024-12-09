
# AstroLogix User Service

## Overview
This microservice is responsible for user management in the AstroLogix platform. It includes endpoints for user CRUD operations, integration with PostgreSQL, and is containerized using Docker.

## Prerequisites
- Java 21 (ensure `JAVA_HOME` is correctly set)
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL (or Docker containerized PostgreSQL)

## Setup

1. Clone the repository.
2. Copy `.env.example` to `.env` and update the database credentials.
3. Build the project:
   ```bash
   mvn clean install
   ```

4. Start the services:
   ```bash
   docker-compose up --build
   ```

5. Access the application at `http://localhost:8080`.

## Running Tests
Run the following to execute tests:
```bash
mvn test
```

## Docker
Ensure the `Dockerfile` and `docker-compose.yml` are correctly set for containerized builds.

