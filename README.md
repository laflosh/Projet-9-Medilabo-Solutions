
# 🩺 MediLabo Solutions

This is the **final project (Project 9)** of the [OpenClassrooms Java Developer Path]  
The application helps determine a patient's **risk of developing type 2 diabetes**, based on their medical file and follow-up notes.

## 🧠 Project Purpose

The goal of this project is to:
- Assess a patient's **diabetes risk level** using a rule-based algorithm.
- Manage **patient records**.
- Add **medical follow-up notes** to patient files.
- Secure and expose these features through a **microservices-based architecture**.

---

## ⚙️ Architecture Overview

This application follows a **microservices architecture**, with an **API Gateway** as the single point of entry.

Each service is **containerized with Docker**, and the full application is orchestrated using **Docker Compose**.

### 🔐 Security

- Authentication and authorization are handled via **JWT (JSON Web Token)**.
- The **Gateway Service** manages login, token generation, and validation using **Spring Security**.

---

## 🧱 Services Overview

| Service        | Description                                                                 | Database |
|----------------|-----------------------------------------------------------------------------|----------|
| **Gateway**    | Entry point for all requests. Handles routing, authentication, and token management via Spring Security. | — |
| **Client UI**  | Front-end built with Thymeleaf. Provides a complete interface for managing patient records. | — |
| **Patient**    | Manages CRUD operations for patient entities.                              | MySQL    |
| **Note**       | Manages CRUD operations for medical notes attached to patients.            | MongoDB  |
| **User**       | Manages user accounts and access rights. Used for authentication.          | MySQL    |
| **Risk**       | Calculates the diabetes risk level. Communicates with other services via Feign. | — |
| **Eureka**     | Service registry to monitor active service instances.                      | — |

---

## 🔧 Installation & Run (Docker)

> 💡 Make sure you have Docker and Docker Compose installed on your machine.

1. Clone the repository:
   ```bash
   git clone https://github.com/laflosh/Projet-9-Medilabo-Solutions.git
   cd Projet-9-Medilabo-Solutions
   ```

2. Add a `.env` file at the project root:
   for example:
   ```
   SECRET_KEY=your-secret-key
   DB_USER=your-db-user
   DB_PASSWORD=your-db-password
   ```
   Watch the docker-compose file to see what is needed for the project run correctly

3. Build and run all services:
   ```bash
   docker-compose up --build
   ```

---

## 🧪 Technologies Used

- **Java / Spring Boot**
- Spring Cloud Gateway
- Spring Data JPA
- Spring Security
- Spring WebFlux
- Spring Web Starter
- Thymeleaf
- Feign
- JWT (JSON Web Token)
- MySQL
- MongoDB
- Eureka Server
- Docker / Docker Compose

---

## 📁 Project Structure

```
medilabo-solutions/
├── gateway-service/
├── clientui-service/
├── patient-service/
├── note-service/
├── user-service/
├── risk-service/
├── eureka-server/
├── docker-compose.yml
└── .env
```

---

## 📌 Notes

- Each service includes its own `Dockerfile` for containerization.
- Database schema/data initialization scripts are included in their respective services.
- The Risk service does **not** persist any data — it only performs calculations based on info from other services.

---

## 📬 Contact

For any questions or feedback, feel free to open an [issue](https://github.com/laflosh/Projet-9-Medilabo-Solutions/issues) or contact me directly.
