#!/bin/bash

# Function to check if a container is running
check_container() {
    local container_name=$1
    if [ "$(docker ps -q -f name=${container_name})" ]; then
        echo "✔ Container ${container_name} is running."
    else
        echo "✘ Container ${container_name} is not running. Check logs for more details."
        exit 1
    fi
}

# Step 1: Pull necessary images and start containers
echo "Starting Docker containers..."
docker-compose up -d --build

# Step 2: Wait for containers to stabilize
echo "Waiting for containers to stabilize..."
sleep 10

# Step 3: Verify containers
echo "Verifying containers..."
check_container "user-service-app-1"
check_container "user-service-db-1"

# Step 4: Verify Flyway migrations
echo "Checking Flyway migrations..."
docker-compose logs flyway | grep "Successfully applied" || {
    echo "✘ Flyway migrations did not run successfully."
    exit 1
}

# Step 5: Connect to the database and check schema
echo "Connecting to Postgres database to verify..."
docker exec -it user-service-db-1 psql -U chloedeguzman -d astrologix -c "SELECT * FROM flyway_schema_history;" || {
    echo "✘ Database verification failed. Ensure migrations ran properly."
    exit 1
}

echo "✔ Database and migrations verified successfully."

# Step 6: Test application container health
echo "Testing application container health..."
docker exec -it user-service-app-1 curl -f http://localhost:8080/actuator/health || {
    echo "✘ Application health endpoint not reachable. Check logs."
    exit 1
}

echo "✔ Setup complete and verified successfully!"
