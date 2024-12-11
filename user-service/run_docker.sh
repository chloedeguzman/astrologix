#!/bin/bash

# Docker Run Script for Clean State Testing

# Step 1: Clean up existing containers, volumes, and networks
echo "Stopping and removing any existing containers, volumes, and networks..."
docker-compose down -v

# Step 2: Prune unused networks
echo "Removing unused networks..."
docker network prune -f

# Step 3: Start Docker containers
echo "Starting Docker containers..."
docker-compose up -d --build

# Step 4: Wait for containers to stabilize
echo "Waiting for containers to stabilize..."
sleep 10

# Step 5: Verify containers
check_container() {
    local container_name=$1
    if [ "$(docker ps -q -f name=${container_name})" ]; then
        echo "✔ Container ${container_name} is running."
    else
        echo "✘ Container ${container_name} is not running. Check logs for more details."
        exit 1
    fi
}

check_container "user-service-db-1"
check_container "user-service-app-1"

# Step 6: Verify Flyway migrations
echo "Verifying Flyway migrations..."
if ! docker-compose logs flyway | grep -q "Successfully applied"; then
    echo "✘ Flyway migrations did not run successfully. Check logs for details."
    exit 1
fi

# Step 7: Output run summary
echo "Run complete. Verify the application is running at http://localhost:8080 and Swagger at http://localhost:8080/swagger-ui.html."
