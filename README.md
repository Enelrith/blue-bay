# Blue Bay

A comprehensive web application for short-term rental management of apartments, built with Spring Boot

## 📋 Overview

Blue Bay is a full-featured property rental management system that enables users to browse, book, and review short-term rental properties. The platform includes complete user management, property listings, booking workflows with integrated payment processing, and a review system.

## 🚀 Current Features

### User Management
- **Authentication & Authorization**
  - User registration with BCrypt password encryption
  - JWT-based authentication (access & refresh tokens)
  - Role-based access control (USER, ADMIN, MODERATOR, COMPLETED_ACCOUNT)
  - Token refresh mechanism
  - Rate limiting per endpoint and user

- **User Profiles**
  - Extended user information (name, date of birth, nationality)
  - ID document verification (Passport or National ID)
  - Profile completion tracking
  - Email and password management

### Property Management
- **Property Listings**
  - Multiple property types (Studio, Single Bedroom, Double Bedroom)
  - Detailed property information (ATAK/AMA numbers, location, size)
  - Geographic coordinates (latitude/longitude)
  - Pricing configuration (nightly rate, cleaning fees)
  - Active/inactive property status
  - Property image management

- **Amenities System**
  - Configurable amenity catalog
  - Property-specific amenity assignments with quantities
  - Searchable amenity features

- **Advanced Search**
  - Filter by property type, price range, size
  - Availability checking for specific date ranges
  - Location-based filtering
  - Active property filtering

### Booking System
- **Reservations**
  - Date range booking with check-in/check-out
  - Multiple booking sources (Website, Airbnb, Booking.com, Walk-in, Phone)
  - Payment type selection (Credit Card, Debit Card, Cash)
  - Booking status tracking (Pending, Confirmed, Checked In, Checked Out, Cancelled, Refunded, Failed)
  - Overlap prevention
  - Minimum stay validation (2 days)

- **Pricing Calculations**
  - Automated net payment calculation
  - VAT and municipality tax application
  - Climate resilience fee calculation (seasonal, property size-based)
  - Total payment computation

### Payment Integration
- **Stripe Integration**
  - Secure checkout session creation
  - Payment confirmation via webhooks
  - Session expiration handling (30 minutes)
  - Automatic booking status updates
  - Success/failure landing pages

### Review System
- **User Reviews**
  - Property rating (1-5 stars)
  - Written review descriptions
  - User-property review uniqueness
  - Edit and delete capabilities
  - Timestamp tracking (created/edited)

### Security & Performance
- **Security Features**
  - Spring Security configuration
  - Method-level security with @PreAuthorize
  - User-specific data access controls
  - Stateless session management

- **Performance Optimization**
  - Request rate limiting (Bucket4j)
  - Endpoint-specific rate limits
  - User and IP-based throttling
  - Entity graph optimization for queries

### API Documentation
- **Swagger/OpenAPI Integration**
  - Interactive API documentation
  - Schema definitions
  - Example requests/responses
  - Bearer token authentication support
  - Accessible at `/swagger-ui.html`

## 🛠️ Technology Stack

### Backend
- **Java 21** - Programming language
- **Spring Boot 4.0.1** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence with Hibernate
- **JWT (jjwt 0.12.6)** - Stateless authentication
- **Maven** - Dependency management and build tool

### Database
- **MySQL** - Primary database
- **Flyway** - Database migration management

### Payment Processing
- **Stripe (v31.1.0)** - Payment gateway integration

### Additional Libraries
- **MapStruct 1.6.3** - DTO mapping
- **Lombok** - Boilerplate code reduction
- **Bucket4j 8.16.0** - Rate limiting
- **Springdoc OpenAPI 2.8.14** - API documentation
- **Thymeleaf** - Template engine for landing pages

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

- **Java 21** or higher
- **Maven 3.6+**
- **MySQL 8.0+**
- **Git**
- **Stripe Account** (for payment processing)

## ⚙️ Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/enelrith/blue-bay.git
cd blue-bay
```

### 2. Configure Environment Variables

Create `application.properties` from the example file:
```bash
cp application.properties.example application.properties
```

Update `application.properties` with your configuration:
```properties
# Database Configuration
DB_URL=jdbc:mysql://localhost:3306/bluebay
DB_USER=your_database_username
DB_PASSWORD=your_database_password

# JWT Configuration (generate with OpenSSL)
JWT_SECRET_KEY=your_base64_encoded_secret_key
JWT_ACCESS_EXPIRATION=900000      # 15 minutes
JWT_REFRESH_EXPIRATION=604800000  # 7 days

# Stripe Configuration
STRIPE_PUBLIC_KEY=pk_test_your_public_key
STRIPE_SECRET_KEY=sk_test_your_secret_key
STRIPE_WEBHOOK_KEY=whsec_your_webhook_secret

# File Storage
PROPERTY_IMAGES_PATH=/path/to/property/images

# Application URL
WEBSITE_URL=http://localhost:8080
```

### 3. Generate JWT Secret Key (Optional)
```bash
openssl rand -base64 32
```

### 4. Configure Flyway

Create `flyway.conf` from the example:
```bash
cp src/main/resources/flyway.conf.example src/main/resources/flyway.conf
```

Update `src/main/resources/flyway.conf`:
```properties
flyway.url=jdbc:mysql://localhost:3306/bluebay
flyway.user=your_database_user
flyway.password=your_database_password
```

### 5. Create Database
```sql
CREATE DATABASE blue_bay;
```

### 6. Run Database Migrations
```bash
mvn flyway:migrate
```

### 7. Build the Project
```bash
mvn clean install
```

### 8. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔌 API Endpoints

### Authentication

#### Register User
```http
POST /users/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Response:** `201 Created`

#### Login
```http
POST /users/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "email": "user@example.com"
}
```

#### Refresh Access Token
```http
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer"
}
```

### User Management

#### Get User by ID
```http
GET /users/{id}
Authorization: Bearer <access_token>
```

**Response:** `200 OK`
```json
{
  "email": "user@example.com"
}
```

#### Update User Email
```http
PUT /users/{id}/email
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "email": "newemail@example.com"
}
```

#### Update User Password
```http
PUT /users/{id}/password
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "password": "newSecurePassword123"
}
```

#### Add User Information (Complete Profile)
```http
POST /users/{id}/user-information
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-01-01",
  "nationality": "Greek",
  "idDocumentType": "PASSPORT",
  "idDocumentNumber": "AE123456"
}
```

**Response:** `201 Created`

#### Update User Information
```http
PATCH /users/{id}/user-information
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "firstName": "Jane",
  "nationality": "French"
}
```

### Property Management

#### Get All Properties
```http
GET /properties
```

**Response:** `200 OK`
```json
[
  {
    "atakNumber": "12345676543",
    "amaNumber": "13245316548",
    "squareMeters": 75.00,
    "type": "STUDIO",
    "street": "Main Street",
    "city": "Athens",
    "postalCode": "12345",
    "country": "Greece",
    "isActive": true,
    "region": "Alimos",
    "latitude": 37.912345,
    "longitude": 23.712345,
    "nightlyRate": 70.00,
    "cleaningFee": 15.00
  }
]
```

#### Get Property by AMA Number
```http
GET /properties/{amaNumber}
```

#### Search Properties with Filters
```http
GET /properties/specs?propertyType=STUDIO&minPrice=50&maxPrice=100&checkIn=2026-03-12T11:15&checkOut=2026-03-15T10:00
```

#### Add Property (Admin Only)
```http
POST /properties
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "atakNumber": "12345676543",
  "amaNumber": "13245316548",
  "squareMeters": 75.00,
  "type": "STUDIO",
  "street": "Main Street",
  "city": "Athens",
  "postalCode": "12345",
  "country": "Greece",
  "region": "Alimos",
  "latitude": 37.912345,
  "longitude": 23.712345,
  "nightlyRate": 70.00,
  "cleaningFee": 15.00
}
```

#### Update Property (Admin Only)
```http
PATCH /properties/{amaNumber}
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "nightlyRate": 80.00,
  "isActive": true
}
```

#### Delete Property (Admin Only)
```http
DELETE /properties/{amaNumber}
Authorization: Bearer <admin_access_token>
```

### Amenities

#### Add Amenity (Admin Only)
```http
POST /amenities
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "name": "Wi-Fi"
}
```

#### Add Amenity to Property (Admin Only)
```http
POST /properties/{propertyId}/amenities/{amenityId}
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "quantity": 1
}
```

#### Update Property Amenity Quantity (Admin Only)
```http
PUT /properties/amenities/{id}
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "quantity": 2
}
```

### Bookings

#### Get User Bookings
```http
GET /bookings/{userId}
Authorization: Bearer <access_token>
```

**Response:** `200 OK`
```json
[
  {
    "checkIn": "2026-03-12T11:15:00",
    "checkOut": "2026-03-15T10:00:00",
    "status": "CONFIRMED",
    "netPayment": 225.00,
    "totalPayment": 281.25,
    "bookingPaymentType": "CREDIT_CARD",
    "source": "BLUE_BAY_WEBSITE",
    "taxes": 56.25,
    "totalClimateFee": 4.50
  }
]
```

#### Create Booking
```http
POST /bookings/{userId}/{propertyId}
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "checkIn": "2026-03-12T11:15",
  "checkOut": "2026-03-15T10:00",
  "bookingPaymentType": "CREDIT_CARD",
  "source": "BLUE_BAY_WEBSITE"
}
```

**Response:** `200 OK`
```json
{
  "bookingId": 123,
  "checkoutUrl": "https://checkout.stripe.com/pay/cs_test_..."
}
```

#### Update Booking Status (Admin Only)
```http
PATCH /bookings/{id}
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "status": "CHECKED_IN"
}
```

#### Cancel Booking
```http
PATCH /bookings/{id}/cancellation
Authorization: Bearer <access_token>
```

### Reviews

#### Add Review
```http
POST /reviews/{propertyId}
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "score": 5,
  "description": "Amazing property with a great view!"
}
```

#### Edit Review
```http
PATCH /reviews/{reviewId}
Authorization: Bearer <access_token>
Content-Type: application/json

{
  "score": 4,
  "description": "Updated: Great property overall."
}
```

#### Delete Review
```http
DELETE /reviews/{reviewId}
Authorization: Bearer <access_token>
```

#### Get User Reviews
```http
GET /reviews/user/{userId}
Authorization: Bearer <access_token>
```

#### Get Property Reviews
```http
GET /reviews/property/{propertyId}
```

### Roles (Admin Only)

#### Get All Roles
```http
GET /roles
Authorization: Bearer <admin_access_token>
```

#### Add Role
```http
POST /roles
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "name": "MODERATOR"
}
```

#### Add Role to User
```http
PATCH /users/{id}/roles
Authorization: Bearer <admin_access_token>
Content-Type: application/json

{
  "name": "COMPLETED_ACCOUNT"
}
```

## 📊 Database Schema

### Core Entities

#### users
- id (bigint, PK)
- email (varchar, unique)
- password (varchar, encrypted)
- created_at (timestamp)
- updated_at (timestamp)
- verified_at (timestamp, nullable)

#### user_information
- id (bigint, PK)
- first_name (varchar)
- last_name (varchar)
- date_of_birth (date)
- nationality (varchar)
- id_document_type (enum: NATIONAL_ID, PASSPORT)
- id_document_number (varchar, unique)
- completed_at (timestamp)
- user_id (bigint, FK)

#### properties
- id (int, PK)
- atak_number (varchar)
- ama_number (varchar, unique)
- square_meters (decimal)
- type (enum: STUDIO, SINGLE_BEDROOM, DOUBLE_BEDROOM)
- street, city, postal_code, country, region (varchar)
- is_active (boolean)
- latitude (decimal)
- longitude (decimal)
- nightly_rate (decimal)
- cleaning_fee (decimal)

#### bookings
- id (bigint, PK)
- user_id (bigint, FK)
- property_id (int, FK)
- check_in (datetime)
- check_out (datetime)
- status (enum)
- net_payment (decimal)
- total_payment (decimal)
- payment_type (enum)
- source (enum)
- taxes (decimal)
- total_climate_fee (decimal)

#### reviews
- id (bigint, PK)
- score (tinyint, 1-5)
- description (nvarchar)
- created_at (timestamp)
- edited_at (timestamp)
- user_id (bigint, FK)
- property_id (int, FK)

#### amenities
- id (int, PK)
- name (varchar, unique)

#### property_amenities
- id (int, PK)
- quantity (int)
- property_id (int, FK)
- amenity_id (int, FK)

#### property_images
- id (int, PK)
- name (varchar)
- property_id (int, FK)

#### roles
- id (int, PK)
- name (enum, unique)

#### refresh_tokens
- id (bigint, PK)
- token (varchar, unique)
- created_at (datetime)
- expires_at (datetime)
- is_revoked (boolean)
- user_id (bigint, FK)

#### booking_fees
- id (bigint, PK)
- type (varchar)
- amount (decimal)
- description (varchar)

## 🗺️ Roadmap

### Phase 1: Core Functionality ✅
- [x] Database setup
- [x] User registration and authentication
- [x] User profile management
- [x] Property listings management
- [x] Property search and filtering
- [x] Amenities system

### Phase 2: Booking System ✅
- [x] Booking creation and management
- [x] Payment integration (Stripe)
- [x] Pricing calculations
- [x] Fee management

### Phase 3: Reviews & Ratings ✅
- [x] Review system
- [x] Rating functionality
- [x] Review moderation

### Phase 4: Enhancements 🚧
- [ ] Email notifications
- [ ] Property availability calendar
- [ ] Advanced analytics dashboard
- [ ] Booking modification system
- [ ] Refund processing
- [ ] Multi-language support
- [ ] Enhanced image management

## 📝 Project Structure

```
blue-bay/
├── src/
│   ├── main/
│   │   ├── java/io/github/enelrith/bluebay/
│   │   │   ├── BlueBayApplication.java          # Main Entry Point
│   │   │   ├── amenities/                        # Amenity Management
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── mappers/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   ├── bookings/                         # Booking & Reservation Logic
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── mappers/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   ├── enums/                            # Domain Constants
│   │   │   ├── exceptions/                       # Global Exception Handling
│   │   │   ├── payment/                          # Payment Processing
│   │   │   │   ├── controllers/
│   │   │   │   ├── stripe/
│   │   │   │   │   ├── config/
│   │   │   │   │   ├── controllers/
│   │   │   │   │   ├── dto/
│   │   │   │   │   ├── exceptions/
│   │   │   │   │   └── services/
│   │   │   │   └── interfaces/
│   │   │   ├── properties/                       # Property Management
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── mappers/
│   │   │   │   ├── repositories/
│   │   │   │   │   └── specifications/
│   │   │   │   └── services/
│   │   │   ├── reviews/                          # Review System
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── mappers/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   ├── roles/                            # Role Management
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── mappers/
│   │   │   │   ├── repositories/
│   │   │   │   └── services/
│   │   │   ├── security/                         # Security & Authentication
│   │   │   │   ├── config/
│   │   │   │   ├── controllers/
│   │   │   │   ├── dto/
│   │   │   │   ├── entities/
│   │   │   │   ├── exceptions/
│   │   │   │   ├── filters/
│   │   │   │   ├── repositories/
│   │   │   │   ├── services/
│   │   │   │   └── utilities/
│   │   │   ├── swagger/                          # API Documentation Config
│   │   │   │   └── config/
│   │   │   └── users/                            # User Management
│   │   │       ├── controllers/
│   │   │       ├── dto/
│   │   │       ├── entities/
│   │   │       ├── exceptions/
│   │   │       ├── mappers/
│   │   │       ├── repositories/
│   │   │       └── services/
│   │   └── resources/
│   │       ├── db/migration/                     # Flyway Migrations (V1-V11)
│   │       ├── templates/                        # Thymeleaf Templates
│   │       ├── application.yaml                  # Application Configuration
│   │       └── flyway.conf.example               # Flyway Template
│   └── test/                                     # Unit & Integration Tests
├── application.properties.example                # Environment Config Template
├── pom.xml                                       # Maven Dependencies
├── mvnw                                          # Maven Wrapper
└── README.md                                     # This File
```

## 🔒 Security Considerations

- Passwords are encrypted using BCrypt (strength: 10)
- JWT tokens have configurable expiration times
- Refresh tokens are stored securely in the database
- Method-level authorization using @PreAuthorize
- Rate limiting prevents API abuse

## 🧪 Testing

Run all tests:
```bash
mvn test
```

Run integration tests:
```bash
mvn verify
```

## 🐛 Known Issues

- Refund processing not yet implemented
- Email notifications pending
- Property image storage requires manual directory setup

---

**Status:** 🚧 In Active Development

**Last Updated:** January 16, 2026