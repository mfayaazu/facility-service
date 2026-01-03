# Facility Service (Multi-Tenant Issue Tracker)

A backend service for reporting and managing facility issues ---
designed as a reusable, secure, multi-tenant platform (companies /
property managers / public institutions).

## 🚀 Tech Stack

-   Java 21\
-   Spring Boot 4
-   Spring Security (JWT)
-   Spring Data JPA
-   PostgreSQL (Docker)
-   Lombok

------------------------------------------------------------------------

## ▶️ Run Locally

### 1️⃣ Start PostgreSQL (Docker)

Create (or use) `docker-compose.yml`:

``` yaml
services:
  postgres:
    image: postgres:16
    container_name: facility-db
    restart: unless-stopped
    environment:
      POSTGRES_DB: facility
      POSTGRES_USER: facility
      POSTGRES_PASSWORD: facility
    ports:
      - "5432:5432"
    volumes:
      - facility_data:/var/lib/postgresql/data

volumes:
  facility_data:
```

Start:

``` bash
docker compose up -d
```

------------------------------------------------------------------------

### 2️⃣ Configure Spring Boot

`src/main/resources/application.yml`

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/facility
    username: facility
    password: facility

  jpa:
    hibernate:
      ddl-auto: update       # dev only
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

> ❗ Do not commit real passwords or tokens.\
> Keep secrets in local files or environment variables.

------------------------------------------------------------------------

### 3️⃣ Seed Demo Data (optional)

Use the demo SQL provided in `/docs/demo-seed.sql` (or the SQL we used
during development).

Demo tenant:

    tenant: demo

Demo users:

    admin@example.com
    staff@example.com
    viewer@example.com

(Passwords intentionally not real --- replace in your environment.)

------------------------------------------------------------------------

## 📌 API Overview

Authentication

    POST /api/auth/login

Core

    GET  /api/dashboard/summary
    GET  /api/issues
    POST /api/issues
    GET  /api/locations

Each request is tenant-aware and protected by JWT.

------------------------------------------------------------------------

## 🛡️ Architecture Highlights

-   Multi-tenant aware design
-   Role-based access (ADMIN / STAFF / VIEWER)
-   Friendly reference numbers (INC123...)
-   RFC-9457 style error responses
-   Attachments + comments
-   Dashboard metrics API
-   PostgreSQL persistence (no in-memory data loss)

------------------------------------------------------------------------

## 🧪 Development Notes

-   Prefer `ddl-auto=update` only for development
-   For production, use Flyway migrations
-   Avoid committing:
    -   `.env`
    -   production configs
    -   secrets / API keys / private JWT keys

------------------------------------------------------------------------

## 📄 License

Internal project --- license to be defined later.
