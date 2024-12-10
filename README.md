## AstroLogix 🌌

AstroLogix is a distributed astrology insights platform designed to deliver real-time, personalized astrological insights and compatibility analyses. The platform is built with a **microservices architecture**, leveraging modern cloud technologies for scalability and resilience.

---

### 🌟 **Features**

- **Astrological Insights:** Get detailed zodiac-based predictions.
- **User Compatibility:** Discover compatibility with others based on zodiac data.
- **Microservices Architecture:** Modular design with separate services for users, astrology insights, and notifications.
- **Cloud-Ready:** Deployable on AWS with Docker and Kubernetes for scalability.
- **Real-Time Updates:** Kafka-driven event-based notifications.

---

### 🛠️ **Technology Stack**

- **Backend:** Java 21, Spring Boot
- **Data Storage:** PostgreSQL
- **Messaging:** Kafka
- **Caching:** Redis
- **Cloud:** AWS (EC2, S3, RDS, Lambda)
- **Deployment:** Docker, Kubernetes
- **Testing:** JUnit 5, TestContainers, JaCoCo
- **CI/CD:** GitHub Actions

---

### 🚀 **Setup Instructions**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-organization/astrologix.git
   cd astrologix
   ```

2. **Run the Services**
   - Build and run each service using Maven and Docker:
     ```bash
     cd astrology-service
     mvn clean package
     docker-compose up
     ```
     Repeat for `user-service` and other services.

3. **Environment Variables**
   Set up a `.env` file in the root of the project:
   ```
   DB_USERNAME=<your-db-username>
   DB_PASSWORD=<your-db-password>
   KAFKA_BROKER=<your-kafka-broker-url>
   ```

4. **Run Tests**
   ```bash
   mvn test
   ```

5. **Generate Test Coverage Report**
   ```bash
   mvn clean verify
   ```
   View the coverage report at `target/site/jacoco/index.html`.

---

### 🧪 **Testing and Coverage**

- **Unit Tests:** Ensuring code correctness.
- **Integration Tests:** Validating interactions between components.
- **Coverage Reports:** Check test coverage in `target/site/jacoco/index.html`.

---

### 🎯 **Goals**

- **Modular Design:** Maintain clear separation between domains like astrology, user management, and notifications.
- **High Scalability:** Handle high traffic with asynchronous messaging and cloud deployment.
- **Real-Time Features:** Push live updates with Kafka and WebSockets.
- **Advanced Analytics:** Provide insights and trends based on user behavior and astrology data.

---

### 📚 **Services Overview**

#### 1. Astrology Service
Handles astrological calculations and delivers insights based on zodiac signs.
- Endpoints:
   - `/api/astrology/zodiac?date=MM-DD`: Fetch zodiac details for a given date.
- Coverage: 90%+

#### 2. User Service
Manages user profiles and preferences.
- Endpoints:
   - `/api/users`: User CRUD operations.
- Coverage: 85%+

---

### 🛡️ **Contributing**

1. Fork the repository.
2. Create a feature branch.
3. Commit changes with clear messages.
4. Submit a pull request for review.
