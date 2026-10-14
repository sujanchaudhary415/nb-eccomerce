# 🛒 E-Commerce REST API

A production-oriented **E-Commerce REST API** built with Java and Spring Boot. The project focuses on secure REST API design, database relationships, order processing, inventory management, and real-world backend development practices.

> 🚧 React frontend is planned/in progress.

## ✨ Features

* 🔐 JWT authentication with HTTP-only cookies
* 👥 Role-based authorization: `USER`, `SELLER`, `ADMIN`
* 📦 Product & category management
* 🛒 Shopping cart
* 📋 Order management
* 💳 Payment management
* 📦 Stock validation & inventory management
* 🔒 Pessimistic locking to help prevent overselling
* 🗑️ Soft delete
* 📄 Pagination
* ⚠️ Global exception handling
* 🧪 Unit testing with JUnit & Mockito
* 📖 Swagger/OpenAPI documentation
* 🐳 Docker & Docker Compose

## 🛠️ Tech Stack

| Technology                  | Purpose                        |
| --------------------------- | ------------------------------ |
| Java 21                     | Programming language           |
| Spring Boot                 | Backend framework              |
| Spring Security             | Authentication & authorization |
| Spring Data JPA / Hibernate | ORM & database access          |
| PostgreSQL                  | Database                       |
| JWT                         | Authentication                 |
| MapStruct                   | DTO mapping                    |
| JUnit / Mockito             | Testing                        |
| Swagger / OpenAPI           | API documentation              |
| Docker                      | Containerization               |
| Maven                       | Build tool                     |

## 🏗️ Architecture

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL
```

The application follows a layered architecture using DTOs, services, repositories, and centralized exception handling.

## 🔐 Security

* JWT access and refresh tokens
* HTTP-only cookies
* BCrypt password hashing
* Role-based authorization
* Seller ownership validation
* Admin-level access where required

Access token: **15 minutes**
Refresh token: **7 days**

## 📦 Order & Inventory

The order system validates stock before creating an order and uses pessimistic locking for stock-sensitive operations.

```text
Cart
 ↓
Validate Stock
 ↓
Lock Product
 ↓
Create Order
 ↓
Create Order Items
 ↓
Reduce Stock
 ↓
Create Payment
```

Order items also store the product price at the time of purchase, preserving historical order prices.

## 📖 API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## 🐳 Run with Docker

Build and start the application:

```bash
docker compose up --build
```

Stop the containers:

```bash
docker compose down
```

The application runs on:

```text
http://localhost:8080
```

## 📁 Project Structure

```text
src/main/java/com/eccomerce/nb_eccomerce
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
└── util
```

## 🚀 Future Improvements

* React frontend
* Production deployment
* CI/CD pipeline
* Integration testing with Testcontainers
* Flyway database migrations
* Product search and filtering

## 👨‍💻 Author

**Sujan Chaudhary**

Java / Spring Boot Backend Developer
