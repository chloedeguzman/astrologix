
# Astrologix User Service

This project is a Spring Boot microservice that manages user data.

## Prerequisites

- Docker & Docker Compose
- Java 21+
- Maven

## Running the Application

1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd astrologix
   ```

2. Create a `.env` file in the `user-service` directory with the following:
   ```env
   DB_USERNAME=your_username
   DB_PASSWORD=your_password
   ```

3. Build and start the services:
   ```bash
   docker-compose up --build
   ```

4. Access the application:
   - App: [http://localhost:8080](http://localhost:8080)
   - Database (Postgres): Exposed on port `5432`

## Running Tests

1. Run Maven tests locally:
   ```bash
   mvn clean test
   ```

2. Integration Tests:
   Integration tests use H2 as an in-memory database for fast testing.

## Notes

- To rebuild the database, use `docker-compose down -v` to clear volumes.
