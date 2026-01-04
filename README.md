# Blue Bay

A web application for short-term rental of apartments, built with Spring Boot

## 📋 Overview

Blue Bay allows users to sign up and rent apartments for short-term stays.

## 🚀 Current Features

- **User Authentication & Authorization**
  - User registration with secure password encryption (BCrypt)
  - JWT authentication
  - Spring Security

- **Database Structure**
  - Initial database schema
  - Entity models for core functionality
  - Relational database design

## 🛠️ Technology Stack

### Backend
- **Java** - Programming language
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **JWT (JSON Web Tokens)** - Stateless token-based authentication
- **Maven** - Dependency management

### Database
- **MySQL** - Database Management System

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

- Java 21 or higher
- Maven
- MySQL
- Git
- OpenSSL (Optional - for JWT secret key generation)

## ⚙️ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/enelrith/blue-bay.git
   cd blue-bay
   ```

2. **Configure the database.properties and flyway.conf files**
   
   Rename `src/database.properties.example` to `src/database.properties`
   Update `src/database.properties` with your database credentials:
   ```properties
   DB_URL=database_url
   DB_USER=database_username
   DB_PASSWORD=your_password

   JWT_SECRET_KEY=your_generated_key
   JWT_EXPIRATION=your_token_expiration_time_milliseconds
   ```
   
   Rename `src/main/resources/flyway.conf.example` to `src/main/resources/flyway.conf`
   Update `src/main/resources/flyway.conf` with yourdatabase credentials:
   ```configuration
   flyway.url=database_url
   flyway.user=database_user
   flyway.password=database_password

3. **Configure JWT Secret**
   
   Set your JWT secret key in `application.properties`:
   ```properties
   jwt.secret=your-secret-key-here
   jwt.expiration=86400000
   ```

4. **Build the project**
   ```bash
   mvn clean install
   ```

5. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 🔌 API Endpoints

### Authentication

#### Register User
```http
POST /users/register
```
```json
{
  "email": "email@example.com",
  "password": "password123"
}
```

#### Login
```http
POST /users/login
```
```json
{
  "email": "email@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "accessToken": "your_access_jwt_token",
  "refreshToken": "your_refresh_jwt_token",
  "type": "Bearer",
  "email": "email@example.com"
}
```
#### Refresh
```http
POST /auth/refresh
```
```json
{
  "refreshToken": "your_refresh_jwt_token"
}
```

**Response:**
```json
{
"accessToken": "your_new_jwt_acess_token",
"type": "Bearer"
}
```

### Protected Endpoints

Include the JWT token in the Authorization header:
```http
Authorization: Bearer <your-jwt-token>
```

## 📊 Database Schema

Current entities include:

- **User** - User account information
- **UserInformation** - Additional user information
- **Property** - Property information
- **Booking** - Junction table for users and properties. Information about user bookings.
- **RefreshToken** - User refresh JWT token information

## 🗺️ Roadmap

### Phase 1: Core Functionality
- [x] Database setup
- [x] User registration and authentication 
- [ ] Property listings management

### Phase 2: Booking System
- [ ] Booking creation and management
- [ ] Payment integration

## 📝 Project Structure

```
blue-bay/
├── src
│   ├── main
│   │   ├── java/io/github/enelrith/bluebay/
│   │   │   ├── BlueBayApplication.java       # Main Entry Point
│   │   │   ├── bookings/                     # Booking & Reservation logic
│   │   │   │   ├── entities/
│   │   │   │   └── repositories/
│   │   │   ├── enums/                        # Shared Domain Constants (Status, Types)
│   │   │   ├── exceptions/                   # Global API Error Handling
│   │   │   ├── properties/                   # Studio Apartment listings
│   │   │   │   ├── entities/
│   │   │   │   └── repositories/
│   │   │   ├── security/                     # JWT, Auth Filter & Spring Security
│   │   │   │   ├── config/
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── filters/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── services/
│   │   │   │   └── utilities/
│   │   │   └── users/                        # User Profiles & Account Management
│   │   │       ├── controllers/
│   │   │       ├── dto/                      # Request/Response Objects
│   │   │       ├── entities/
│   │   │       ├── mappers/                  # MapStruct/Manual Entity Mappers
│   │   │       ├── repositories/
│   │   │       └── services/
│   │   └── resources/
│   │       ├── db/migration/                 # Flyway Migration Scripts (V1__init...)
│   │       ├── application.yaml              # Application Configuration
│   │       └── flyway.conf.example           # Flyway Template
│   └── test/                                 # Unit & Integration Tests
├── application.properties.example               # Database Config Template
├── pom.xml                                   # Project Dependencies
└── mvnw                                      # Maven Wrapper
└── README.md
```

**Status:** 🚧 In Development

Last Updated: January 2026