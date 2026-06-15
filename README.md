# 💰 Expense Tracker System

A **REST API backend** built with Spring Boot that helps users track their daily expenses, filter them by category or date, and generate spending reports — secured with JWT Authentication.

---

## ✨ Features

### 🔐 Authentication
- User Registration & Login
- JWT-based Stateless Authentication
- Each user can only access their own data

### 💸 Expense Management
- Add, Update, Delete Expenses
- Get Expense by ID
- Get All Expenses (user-specific)

### 🔍 Filtering
- Filter by Category (FOOD, TRAVEL, SHOPPING, BILLS, ENTERTAINMENT, OTHERS)
- Filter by Date Range
- Filter by Month & Year

### 📊 Reports
- Total Monthly Expense
- Category-wise Expense Breakdown
- Highest Spending Category

### 🛡️ Production-Ready
- Global Exception Handling with meaningful error messages
- Input Validation on all request fields
- Proper HTTP Status Codes (201, 400, 401, 404, 500)
- Layered Architecture (Controller → Service → Repository)

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java 17** | Programming Language |
| **Spring Boot 3.x** | Backend Framework |
| **Spring Security 6** | Authentication & Authorization |
| **JWT (jjwt 0.11.5)** | Token-based Auth |
| **Spring Data JPA** | ORM Layer |
| **Hibernate** | JPA Implementation |
| **MySQL** | Relational Database |
| **Lombok** | Boilerplate Reduction |
| **Validation** | Input Validation |
| **Postman** | API Testing |

---

## 📁 Project Structure

```
src/main/java/com/intermediate_project/Expense_Tracker_System_App/
│
├── controller/
│   ├── AuthController.java        # Register, Login APIs
│   └── ExpenseController.java     # CRUD, Filter, Report APIs
│
├── service/
│   ├── AuthService.java           # Auth business logic
│   └── ExpenseService.java        # Expense business logic
│
├── repository/
│   ├── UserRepository.java        # User DB operations
│   └── ExpenseRepository.java     # Expense DB + custom JPQL queries
│
├── model/
│   ├── User.java                  # User Entity
│   ├── Expense.java               # Expense Entity
│   ├── Category.java              # Enum: FOOD, TRAVEL, etc.
│   └── Role.java                  # Enum: ROLE_USER, ROLE_ADMIN
│
├── dto/
│   ├── AuthRegisterRequestDTO.java
│   ├── AuthLoginRequestDTO.java
│   ├── AuthResponseDTO.java
│   ├── ExpenseRequestDTO.java
│   └── ExpenseResponseDTO.java
│
├── security/
│   ├── JwtUtil.java               # Token generate & validate
│   ├── JwtFilter.java             # Intercept every request
│   ├── CustomUserDetailsService.java
│   └── SecurityConfig.java        # Security rules
│
├── exception/
│   ├── ExpenseNotFoundException.java
│   └── GlobalExceptionHandler.java  # @RestControllerAdvice
│
└── ExpenseTrackerSystemAppApplication.java
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven
- Postman (for testing)

### Step 1 — Clone the Repository
```bash
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker
```

### Step 2 — Create MySQL Database
```sql
CREATE DATABASE expense_tracker_db;
```

### Step 3 — Configure application.properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/expense_tracker_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

server.port=8080
```

### Step 4 — Run the Application
```bash
mvn spring-boot:run
```

Tables will be **auto-created** by Hibernate on first run.

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080/api
```

---

### 🔐 Auth APIs

#### Register
```
POST /auth/register
```
**Request Body:**
```json
{
    "name": "Monish Kumar",
    "email": "monish@gmail.com",
    "password": "password123"
}
```
**Response (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "email": "monish@gmail.com",
    "name": "Monish Kumar",
    "role": "ROLE_USER"
}
```

---

#### Login
```
POST /auth/login
```
**Request Body:**
```json
{
    "email": "monish@gmail.com",
    "password": "password123"
}
```
**Response (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "email": "monish@gmail.com",
    "name": "Monish Kumar",
    "role": "ROLE_USER"
}
```

> ⚠️ **All APIs below require this header:**
> ```
> Authorization: Bearer <token>
> ```

---

### 💸 Expense APIs

#### Add Expense
```
POST /expenses
```
**Request Body:**
```json
{
    "title": "Dinner at Restaurant",
    "amount": 850.00,
    "category": "FOOD",
    "date": "2025-06-14",
    "description": "Dinner with friends"
}
```
**Response (201):**
```json
{
    "id": 1,
    "title": "Dinner at Restaurant",
    "amount": 850.0,
    "category": "FOOD",
    "date": "2025-06-14",
    "description": "Dinner with friends",
    "createdAt": "2025-06-14T10:30:00"
}
```

---

#### Get All Expenses
```
GET /expenses
```

#### Get Expense by ID
```
GET /expenses/{id}
```

#### Update Expense
```
PUT /expenses/{id}
```

#### Delete Expense
```
DELETE /expenses/{id}
```
**Response:**
```
Expense deleted successfully
```

---

### 🔍 Filter APIs

#### Filter by Category
```
GET /expenses/filter/category?category=FOOD
```

**Available Categories:**
```
FOOD | TRAVEL | SHOPPING | BILLS | ENTERTAINMENT | OTHERS
```

#### Filter by Date Range
```
GET /expenses/filter/date-range?startDate=2025-06-01&endDate=2025-06-30
```

#### Filter by Month
```
GET /expenses/filter/month?month=6&year=2025
```

---

### 📊 Report APIs

#### Total Monthly Expense
```
GET /expenses/reports/monthly-total?month=6&year=2025
```
**Response:**
```json
3249.0
```

#### Category-wise Breakdown
```
GET /expenses/reports/category-wise
```
**Response:**
```json
{
    "FOOD": 850.0,
    "TRAVEL": 500.0,
    "BILLS": 1200.0,
    "SHOPPING": 699.0
}
```

#### Highest Spending Category
```
GET /expenses/reports/highest-category
```
**Response:**
```
BILLS - ₹1200.0
```

---

## ❌ Error Responses

### 404 — Not Found
```json
{
    "timestamp": "2025-06-14T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "Expense not found with id: 99"
}
```

### 400 — Validation Failed
```json
{
    "timestamp": "2025-06-14T10:30:00",
    "status": 400,
    "error": "Validation Failed",
    "messages": {
        "amount": "Amount must be positive",
        "title": "Title cannot be blank"
    }
}
```

### 401 — Unauthorized
```json
{
    "status": 401,
    "error": "Unauthorized",
    "message": "JWT token is missing or invalid"
}
```

---

## 🗄️ Database Schema

```sql
-- Users Table
CREATE TABLE users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL
);

-- Expenses Table
CREATE TABLE expenses (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    amount      DOUBLE       NOT NULL,
    category    VARCHAR(50)  NOT NULL,
    date        DATE         NOT NULL,
    description VARCHAR(255),
    user_id     BIGINT       NOT NULL,
    created_at  DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

> Tables are auto-created by Hibernate — no need to run SQL manually.

---

## 👨‍💻 Author

**Monish Kumar**
- B.Tech —  NSUT Delhi (2027)
- Java Backend Developer
- GitHub: [@yourusername](https://github.com/Monish078)
- LinkedIn: [linkedin.com/in/yourprofile](https://www.linkedin.com/in/monish-khan786)

---

## 📌 Key Learnings from this Project

- Implementing **JWT Authentication** from scratch in Spring Security 6
- Writing **custom JPQL queries** for filtering and aggregation
- Designing **user-specific data access** using SecurityContext
- Building **GlobalExceptionHandler** for production-ready error responses
- Proper **DTO pattern** to separate API layer from DB layer
