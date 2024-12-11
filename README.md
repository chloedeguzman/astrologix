## AstroLogix 🌌

AstroLogix is a distributed astrology insights platform designed to deliver real-time, personalized astrological insights and compatibility analyses. Built on a **microservices architecture**, the platform demonstrates scalability and resilience using only free and open-source tools.

---

### 🌟 **Features**
- **Astrological Insights:** Get detailed zodiac-based predictions.
- **User Compatibility:** Discover compatibility with others based on zodiac data.
- **Microservices Architecture:** Modular design with separate services for users, astrology insights, and notifications.
- **Local and Cloud-Ready:** Easily deployable on local Docker environments or free-tier cloud platforms.
- **Real-Time Updates:** Kafka-driven event-based notifications.

---

### 🛠️ **Technology Stack**
- **Backend:** Java 21, Spring Boot
- **Data Storage:** PostgreSQL (via Docker Compose)
- **Messaging:** Apache Kafka
- **Caching:** Redis
- **Deployment:** Docker, Kubernetes (using Minikube or k3s for free setups)
- **Testing:** JUnit 5, TestContainers, JaCoCo
- **CI/CD:** GitHub Actions (completely free)

---

### 🚀 **Setup Instructions**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-organization/astrologix.git
   cd astrologix
   ```

2. **Run the Services Locally**

   Create the env file and use the setup and run scripts provided in each service's directory (e.g., `user-service`, `astrology-service`):
   ```bash
   cd user-service
   ./set_up.sh
   ./run_docker.sh
   ```

4. **Run Tests**
   Execute the testing commands in each service directory:
   ```bash
   mvn test
   ```

For service-specific instructions, see the README in each service directory.

---

### 📚 **Services Overview**
- **Astrology Service:** Calculates and delivers astrological insights.
- **User Service:** Manages user profiles and preferences.
- **Notification Service:** Handles real-time event-based notifications.

---

### 🎯 **Goals**
- Modular Design: Maintain clear separation between domains.
- Scalability: Simulate high traffic using Kafka and Dockerized services.
- Real-Time Features: Push live updates with Kafka and WebSockets.

For detailed testing and troubleshooting, see [TESTING.md](TESTING.md).

