#!/bin/bash

# Setup Script for Prerequisites

# Function to install Homebrew packages if missing
install_if_missing() {
    local package=$1
    if ! brew list $package &>/dev/null; then
        echo "Installing $package..."
        brew install $package
    else
        echo "$package is already installed."
    fi
}

# Step 1: Check prerequisites
echo "Checking prerequisites..."
if ! command -v brew &>/dev/null; then
    echo "Homebrew not found. Installing Homebrew..."
    /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
else
    echo "Homebrew is already installed."
fi

# Install required tools
install_if_missing "docker"
install_if_missing "docker-compose"
install_if_missing "postgresql"
install_if_missing "flyway"

# Step 2: Check for required environment files
echo "Checking for required files..."
if [ ! -f ".env" ]; then
    echo ".env file not found. Creating a default .env file."
    cat <<EOT >> .env
DB_USERNAME=defaultuser
DB_PASSWORD=defaultpassword
EOT
    echo "Default .env file created. Please update it with your credentials."
fi

if [ ! -f "docker-compose.yml" ]; then
    echo "docker-compose.yml not found. Ensure it exists in the current directory."
    exit 1
fi

# Step 3: Pull necessary Docker images
echo "Pulling Docker images..."
docker pull postgres:latest
docker pull openjdk:21

# Step 4: Build the JAR file
echo "Building application JAR file..."
if ! mvn clean package -DskipTests; then
    echo "Failed to build the application JAR file. Check Maven logs for details."
    exit 1
fi

# Step 5: Output setup summary
echo "Setup complete. You can now run the application using the Docker run script."