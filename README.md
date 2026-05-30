# 🚗 Ride4Rent — Car Rental Platform
A full-stack car rental web application built with **Spring Boot** and **Next.js**, featuring a multi-role system for clients, business owners, and administrators. Users can browse and book vehicles, business owners can manage their fleet, and administrators have full platform oversight.
---
## 📸 Screenshots
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/ab660c8c-ba82-4b83-be11-5d98eaf96ab6" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/fffae929-92a7-4a2b-9a36-f6d827cbbb7a" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/52bdbd15-2d57-4e96-84a7-43280f8f5e60" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/de042237-5e65-47fa-a2f0-602d18343285" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/4245606c-00fb-4971-93af-25aa32ddc166" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/e0728f40-f025-4afe-a352-053de55fa21c" />
<img width="2048" height="1220" alt="image" src="https://github.com/user-attachments/assets/a5389bc1-1b71-4325-adba-443fe8704791" />
---
## ✨ Features
### 👤 Client
- **Register & Login** with email verification via a secure token sent to the inbox
- **Browse available vehicles** — filter by type, location, and price per day
- **Book a car** by selecting start and end dates
- **Dashboard** — view active bookings, total trips, and recent activity at a glance
- **Upcoming trips** — see all confirmed reservations with car details and dates
- **Transaction history** — full history of past bookings with pricing
- **Review system** — leave a star rating and comment for vehicles you've rented (one review per booking)
- **Profile management** — update personal info, license number, phone, address, and bio
- **Payment methods** — add Visa / Mastercard cards
### 🏢 Business Owner
- **Register as a business** — account goes through admin approval before activation
- **Fleet management** — add, edit, and delete vehicles from a personal dashboard
- **My Bookings** — see all reservations made on your fleet, with client details
- **Earnings overview** — track revenue from confirmed bookings
- **Car details editor** — update price per day, availability, location, and photos
### 🛡️ Admin
- **Dashboard statistics** — total users, vehicles, bookings, and pending business approvals at a glance
- **Business approval workflow** — approve or reject business accounts
- **User management** — view all registered clients and their booking count
- **Vehicle overview** — see all vehicles on the platform with owner info
- **Full booking history** — monitor all reservations platform-wide
- **Complaint management** — handle reported issues (admin panel)
---
## 🏗️ Tech Stack
### Backend
|
 Technology 
|
 Version 
|
 Role 
|
|
---
|
---
|
---
|
|
 Java 
|
 17 
|
 Language 
|
|
 Spring Boot 
|
 3.2.4 
|
 Framework 
|
|
 Spring Security 
|
 — 
|
 Authentication & Authorization 
|
|
 Spring Data JPA 
|
 — 
|
 ORM / Database Access 
|
|
 JWT (jjwt) 
|
 0.11.5 
|
 Stateless token-based auth 
|
|
 PostgreSQL 
|
 — 
|
 Relational Database 
|
|
 Spring Mail 
|
 — 
|
 Email verification 
|
|
 Lombok 
|
 1.18.30 
|
 Boilerplate reduction 
|
|
 Maven 
|
 — 
|
 Build tool 
|
|
 Docker 
|
 — 
|
 Containerization 
|
### Frontend
|
 Technology 
|
 Version 
|
 Role 
|
|
---
|
---
|
---
|
|
 Next.js 
|
 16 
|
 React Framework 
|
|
 React 
|
 19 
|
 UI Library 
|
|
 Next-Auth 
|
 4.x 
|
 Session management 
|
|
 Axios 
|
 1.x 
|
 HTTP Client 
|
|
 CSS Modules 
|
 — 
|
 Scoped styling 
|
---
## 📂 Project Structure
```
uber4rentalApp/
├── uber4backend/               # Spring Boot REST API
│   ├── src/main/java/org/eusebiu/
│   │   ├── controller/         # REST endpoints
│   │   │   ├── UserController.java
│   │   │   ├── VehicleController.java
│   │   │   ├── BookingController.java
│   │   │   ├── ReviewController.java
│   │   │   ├── AdminController.java
│   │   │   └── ImageUploadController.java
│   │   ├── models/             # JPA Entities
│   │   │   ├── User.java
│   │   │   ├── Vehicle.java
│   │   │   ├── Booking.java
│   │   │   ├── Review.java
│   │   │   └── Rating.java
│   │   ├── repository/         # Spring Data Repositories
│   │   ├── service/            # Business logic
│   │   ├── security/           # JWT filter & utilities
│   │   ├── dto/                # Data Transfer Objects
│   │   └── config/             # CORS & Security configuration
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
│
└── uber4frontend/              # Next.js Frontend
    └── src/
        ├── app/
        │   ├── auth/           # Login, Register, Password Recovery
        │   ├── cars/           # Public car listings
        │   └── dashboard/
        │       ├── client/     # Cars, Bookings, Profile, Payment
        │       ├── business/   # Fleet, Bookings, Earnings
        │       └── admin/      # Users, Vehicles, Transactions, Complaints
        └── components/         # Shared UI components
```
---
## 🚀 Getting Started
### Prerequisites
- Java 17+
- Maven 3.9+
- Node.js 18+
- PostgreSQL (or Docker)
---
### 🐳 Run with Docker (Recommended)
```bash
cd uber4backend
docker-compose up --build
```
This starts both the **Spring Boot API** on `http://localhost:8080` and the **PostgreSQL** database.
---
### 🔧 Manual Setup
#### 1. Backend
```bash
cd uber4backend
```
Configure your environment in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5433/uber-db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
jwt.secret=your_jwt_secret_key
```
Then start the server:
```bash
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.
#### 2. Frontend
```bash
cd uber4frontend
npm install
npm run dev
```
The app will be available at `http://localhost:3000`.
---
## 🔐 Authentication Flow
1. User **registers** → backend sends a **verification email** with a unique token
2. User clicks the email link → account is **activated**
3. User **logs in** → backend returns a **JWT token**
4. All subsequent API requests include the JWT in the `Authorization: Bearer <token>` header
5. Business accounts additionally require **admin approval** before they can list vehicles
---
## 🌐 API Endpoints
### Auth & Users — `/api/users`
|
 Method 
|
 Endpoint 
|
 Access 
|
 Description 
|
|
--------
|
----------
|
--------
|
-------------
|
|
 POST 
|
`/register`
|
 Public 
|
 Register a new user 
|
|
 GET 
|
`/verify?token=`
|
 Public 
|
 Verify email address 
|
|
 POST 
|
`/login`
|
 Public 
|
 Login & get JWT 
|
|
 GET 
|
`/profile`
|
 Auth 
|
 Get current user profile 
|
|
 PUT 
|
`/profile`
|
 Auth 
|
 Update user profile 
|
|
 GET 
|
`/dashboard`
|
 Auth 
|
 Get dashboard summary 
|
|
 GET 
|
`/trips/upcoming`
|
 Auth 
|
 Get upcoming reservations 
|
|
 GET 
|
`/transactions`
|
 Auth 
|
 Get transaction history 
|
|
 POST 
|
`/payment-methods`
|
 Auth 
|
 Add a payment method 
|
### Vehicles — `/api/vehicles`
|
 Method 
|
 Endpoint 
|
 Access 
|
 Description 
|
|
--------
|
----------
|
--------
|
-------------
|
|
 GET 
|
`/`
|
 Public 
|
 Get all available vehicles 
|
|
 GET 
|
`/my`
|
 Business 
|
 Get own fleet 
|
|
 POST 
|
`/`
|
 Business 
|
 Add a new vehicle 
|
|
 PUT 
|
`/{id}`
|
 Business / Admin 
|
 Update vehicle 
|
|
 DELETE 
|
`/{id}`
|
 Business / Admin 
|
 Delete vehicle 
|
### Bookings — `/api/bookings`
|
 Method 
|
 Endpoint 
|
 Access 
|
 Description 
|
|
--------
|
----------
|
--------
|
-------------
|
|
 POST 
|
`/`
|
 Client 
|
 Create a booking 
|
|
 GET 
|
`/business/my-bookings`
|
 Business 
|
 Get fleet bookings 
|
### Reviews — `/api/reviews`
|
 Method 
|
 Endpoint 
|
 Access 
|
 Description 
|
|
--------
|
----------
|
--------
|
-------------
|
|
 GET 
|
`/vehicle/{vehicleId}`
|
 Public 
|
 Get reviews for a vehicle 
|
|
 POST 
|
`/`
|
 Client 
|
 Submit a review (must have booked) 
|
### Admin — `/api/admin`
|
 Method 
|
 Endpoint 
|
 Access 
|
 Description 
|
|
--------
|
----------
|
--------
|
-------------
|
|
 GET 
|
`/stats`
|
 Admin 
|
 Platform-wide statistics 
|
|
 GET 
|
`/businesses`
|
 Admin 
|
 All business accounts 
|
|
 PUT 
|
`/businesses/{id}/approve`
|
 Admin 
|
 Approve a business 
|
|
 PUT 
|
`/businesses/{id}/reject`
|
 Admin 
|
 Reject a business 
|
|
 GET 
|
`/clients`
|
 Admin 
|
 All client accounts 
|
|
 GET 
|
`/vehicles`
|
 Admin 
|
 All vehicles 
|
|
 GET 
|
`/bookings`
|
 Admin 
|
 All bookings 
|
---
## 👥 User Roles
|
 Role 
|
 Description 
|
|
------
|
-------------
|
|
`client`
|
 Can browse cars, make bookings, and leave reviews 
|
|
`business`
|
 Can list and manage vehicles, view bookings for their fleet 
|
|
`ADMIN`
|
 Full platform access — manages users, vehicles, and approvals 
|
---
## ☁️ Deployment
The backend is configured for deployment on **Render.com** using Docker.
- The `PORT` environment variable is automatically injected by Render
- Database connection uses environment variables (`DB_URL`, `DB_USER`, `DB_PASSWORD`)
- Multi-stage Docker build keeps the image lean
- JVM is configured with `-Xmx300m` to stay within Render's free tier limits
```bash
# Build the Docker image
docker build -t ride4rent-backend .
```
---
## 📝 License
This project was built for educational and portfolio purposes.
---
<div align="center">
  Built with ❤️ using Spring Boot & Next.js
</div>
