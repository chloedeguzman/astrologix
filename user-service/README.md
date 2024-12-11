# AstroLogix User Service

## Overview
This microservice handles user management in the AstroLogix platform, including:
- User CRUD operations.
- Integration with PostgreSQL for persistence.
- Docker containerization for deployment.

## Prerequisites
Ensure the following tools are installed:
- Java 21 (set `JAVA_HOME` accordingly).
- Maven 3.8+ for building the application.
- Docker and Docker Compose for containerization.

## Setup Instructions

### Step 1: Set Up Environment
Copy the example `.env` file and update the database credentials:
```bash
cp .env.example .env
```

### Step 2: Build the Service
Run the following command to compile and package the application:
```bash
mvn clean install
```

### Step 3: Run the Service
Run the provided setup and Docker scripts:
```bash
./set_up.sh
./run_docker.sh
```

## Endpoints
- **Base URL:** `http://localhost:8080`
- **Swagger Documentation:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Endpoints:**
   - `/api/users`: User CRUD operations.

## Troubleshooting
- **Database Issues:** Ensure `.env` credentials match your database configuration.
- **Container Issues:** Check logs for errors:
  ```bash
  docker-compose logs user-service-app
  ```

For testing and Flyway troubleshooting, see [TESTING.md](../TESTING.md).

