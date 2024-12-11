# AstroLogix Service: Testing & Troubleshooting

## Overview
This document provides guidelines for testing and troubleshooting the AstroLogix, including Flyway migrations, PostgreSQL integration, and container health.

## Testing Instructions

### Running Unit Tests
Run the following command to execute all unit tests:
```bash
mvn test
```

### Validating Database Migrations
1. Check Flyway logs for applied migrations:
   ```bash
   docker logs user-service-flyway-1
   ```
2. Migration scripts should follow this naming convention and be located in `db/migration`:
   ```plaintext
   V1__initial_schema.sql
   V2__add_users_table.sql
   ```
3. Reset the database schema if necessary:
   ```sql
   DROP SCHEMA public CASCADE;
   CREATE SCHEMA public;
   ```

### Running Integration Tests
Use integration test tools like Testcontainers to verify database connectivity and API functionality:
```java
@Container
static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
    .withDatabaseName("testdb")
    .withUsername("testuser")
    .withPassword("testpass");
```

### Performance Testing
Use tools like Apache JMeter or Postman to load test the API.

---

## Troubleshooting

### Common Issues
1. **Flyway Migration Errors**
    - Inspect Flyway logs:
      ```bash
      docker logs user-service-flyway-1
      ```
    - Ensure migration scripts exist and are correctly named.
    - Check the `flyway_schema_history` table:
      ```sql
      SELECT * FROM flyway_schema_history;
      ```

2. **Database Connection Problems**
    - Verify `.env` credentials match the database configuration.
    - Test connectivity using:
      ```bash
      docker exec -it user-service-db-1 psql -U <DB_USERNAME> -d astrologix
      ```

3. **Application Startup Issues**
    - Check `user-service-app` logs:
      ```bash
      docker logs user-service-app-1
      ```

