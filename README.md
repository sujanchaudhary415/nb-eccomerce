# 🛒 E-Commerce REST API Backend

A production-oriented **E-Commerce REST API** built with **Java 21, Spring Boot, Spring Security, and PostgreSQL**. This project demonstrates backend development practices including JWT authentication, role-based authorization, inventory management, transactional order processing, and REST API documentation.

**🔗 Live API:** [View Deployed Backend](https://nb-eccomerce-5.onrender.com))  
**📖 Swagger UI:** [Explore & Test API Endpoints](https://nb-eccomerce-5.onrender.com/swagger-ui/index.html)
**📂 Source Code:** [GitHub Repository](https://github.com/sujanchaudhary415/nb-eccomerce))

> **Note:** The backend is deployed on Render. If you are using Render's free web service, the application may take some time to respond after a period of inactivity.

## ✨ Key Features

- **Authentication & Security**
  - JWT-based authentication using HTTP-only access and refresh token cookies
  - BCrypt password hashing
  - Role-based authorization for `USER`, `SELLER`, and `ADMIN`
  - Seller ownership validation and administrative access controls

- **Product & Category Management**
  - Product and category CRUD operations
  - Seller-specific product management
  - Soft deletion to preserve database records
  - Paginated API responses

- **Shopping Cart & Orders**
  - User-specific shopping carts
  - Order creation and order item management
  - Historical price snapshots for order items
  - Stock validation and inventory updates
  - Order cancellation with stock restoration

- **Inventory & Data Integrity**
  - Pessimistic locking for stock-sensitive operations
  - Transactional service operations
  - Database relationship management using JPA and Hibernate

- **Developer Experience & Maintainability**
  - DTO-based API request and response models
  - MapStruct for object mapping
  - Centralized exception handling
  - Input validation and consistent API responses
  - Unit testing with JUnit 5 and Mockito
  - Interactive API documentation with Swagger/OpenAPI
  - Docker and Docker Compose support

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Application framework |
| Spring Security | Authentication and authorization |
| Spring Data JPA & Hibernate | ORM and database access |
| PostgreSQL | Relational database |
| JWT | Token-based authentication |
| MapStruct | DTO mapping |
| Swagger / OpenAPI | API documentation and testing |
| Docker & Docker Compose | Containerization |
| Maven | Build and dependency management |

## 🏗️ Architecture

The application follows a layered architecture that separates HTTP handling, business logic, persistence, and cross-cutting concerns.

```text
Client / API Consumer
        |
        v
   Controllers
        |
        v
     Services
        |
        v
   Repositories
        |
        v
    PostgreSQL

Supporting components:
- DTOs and MapStruct
- Spring Security and JWT
- Global Exception Handling
- Validation and Utility Classes
```

This separation improves maintainability, testability, and clarity by keeping business logic out of controllers and database access inside repositories.

## 🔐 Authentication & Authorization

The API uses Spring Security with JWT access and refresh tokens stored in HTTP-only cookies.

| Token | Expiration |
|---|---|
| Access token | 15 minutes |
| Refresh token | 7 days |

Additional security measures include:

- BCrypt password hashing
- Role-based access control
- Seller ownership checks for protected resources
- Restricted administrative endpoints
- Centralized authentication and authorization error handling

**Roles**

- `USER`: Access user-level shopping functionality.
- `SELLER`: Manage permitted products and seller resources.
- `ADMIN`: Access administrative functionality.

## 📦 Order Processing & Inventory

The order workflow validates product availability and protects stock-sensitive operations using pessimistic locking.

```text
Shopping Cart
     |
     v
Validate Cart & Stock
     |
     v
Lock Relevant Product Records
     |
     v
Create Order & Order Items
     |
     v
Update Inventory
     |
     v
Create Payment Record
```

Order items preserve the product price at the time of purchase, so historical orders do not depend on subsequent product price changes.

Pessimistic locking and transaction management help prevent conflicting stock updates during concurrent order processing.

## 💳 Payment Management

The API includes payment management for supported payment methods:

- UPI
- Card
- Net banking
- Wallet

Payment records are associated with orders, allowing payment status and related information to be managed as part of the order workflow.

> Payment gateway integration and actual transaction processing should be distinguished from payment record management. Refer to the implementation for the currently supported payment flow.

## 📖 API Documentation & Testing

**Start here: [Open Swagger UI](https://YOUR-RENDER-URL.onrender.com/swagger-ui/index.html)**

Swagger UI lets recruiters and developers explore available endpoints, inspect request and response schemas, and send API requests directly from the browser.

### Suggested exploration order

1. **Authentication:** Register an account and log in to understand the authentication flow.
2. **Categories & Products:** Explore public endpoints and product/category operations.
3. **Cart:** Add products and inspect cart operations.
4. **Orders:** Explore order creation, order details, and cancellation.
5. **Payments:** Inspect payment-related endpoints.
6. **Authorization:** Test protected operations using the appropriate user role.

Some endpoints require authentication or a particular role. Follow the documented request schemas and authorization requirements.

### Testing notes

- Use your own test accounts and sample data.
- Do not use real payment information or personal credentials.
- If the deployed instance is unavailable, try again after a short wait or run the application locally.

## 🚀 Run Locally

### Prerequisites

- Java 21
- Maven
- Docker Desktop with Docker Compose
- Git

### 1. Clone the repository

```bash
git clone https://github.com/sujanchaudhary415/nb-eccomerce.git
cd nb-eccomerce
```

### 2. Configure environment variables

Configure your database credentials and JWT settings using environment variables or your local environment configuration.

Example variable names:

```dotenv
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ecommerce
SPRING_DATASOURCE_USERNAME=your_db_username
SPRING_DATASOURCE_PASSWORD=your_db_password
SPRING_APP_JWTSECRET=your_secure_jwt_secret
```

Ensure that these names match the configuration placeholders used in your application. Set your access-token and refresh-token configuration as required by the project.

**Never commit real database passwords, production JWT secrets, or other credentials to GitHub.**

### 3. Start the application with Docker Compose

```bash
docker compose up --build
```

To stop the containers:

```bash
docker compose down
```

Use the database configuration defined in your Compose file. If you run PostgreSQL through Docker, the application container should connect to the database using the Compose service name, not `localhost`.

### 4. Access the API

| Resource | URL |
|---|---|
| API base URL | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| OpenAPI specification | `http://localhost:8080/v3/api-docs` |

## 📁 Project Structure

```text
src/main/java/com/eccomerce/nb_eccomerce/
├── config/       # Application configuration
├── controller/   # REST API endpoints
├── dto/          # Request and response objects
├── entity/       # JPA entities
├── exception/    # Custom exceptions and error handling
├── mapper/       # MapStruct mappers
├── repository/   # Database access
├── security/     # JWT and Spring Security components
├── service/      # Business logic
└── util/         # Shared utilities
```

## 🧪 Testing

Unit tests use **JUnit 5 and Mockito** to test business logic independently of external infrastructure.

Run the test suite with:

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

If the Maven Wrapper is not included in the repository, use `mvn test` instead.

## 🔮 Future Improvements

- React frontend integration
- Integration testing with Testcontainers
- Database versioning and migrations with Flyway
- Product search, filtering, and sorting
- CI/CD pipeline
- Further production deployment and monitoring improvements

## 👨‍💻 Author

**Sujan Chaudhary**  
Java Backend Developer | Java • Spring Boot • Spring Security • PostgreSQL

- GitHub: [@sujanchaudhary415](https://github.com/sujanchaudhary415)
- LinkedIn: [linkedin.com/in/sujanchaudhary415](https://www.linkedin.com/in/sujanchaudhary415)

---

*Built to demonstrate practical Java backend development, secure API design, and database-driven business logic.*
