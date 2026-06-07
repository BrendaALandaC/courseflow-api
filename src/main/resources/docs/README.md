# CourseFlow API

> RESTful backend for course management, participant enrollment, and certificate generation — built with Java 21 and Spring Boot 3.

[![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![MariaDB](https://img.shields.io/badge/MariaDB-10.x-blue?style=flat-square&logo=mariadb)](https://mariadb.org/)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square&logo=docker)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)](LICENSE)

---

## Table of contents

- [Overview](#overview)
- [Features](#features)
- [Tech stack](#tech-stack)
- [Architecture](#architecture)
- [Getting started](#getting-started)
- [Environment variables](#environment-variables)
- [API reference](#api-reference)
- [Project structure](#project-structure)
- [Roadmap](#roadmap)
- [Author](#author)

---

CourseFlow API is a backend portfolio project built with Java and Spring Boot.

It provides course management, participant enrollment, JWT authentication, certificate generation, Excel reports, file handling, and Dockerized deployment.


## Project Status

In development.



## Features

- **JWT authentication** — stateless auth with access tokens; role-based access for `ADMIN` and `USER` roles
- **Course management** — full CRUD for courses, including modality (online / in-person), capacity, and schedule
- **Participant enrollment** — register participants to courses with status tracking
- **Certificate generation** *(in progress)* — bulk PDF certificate generation per course using Apache POI / iText
- **Excel reports** *(in progress)* — export enrollment metrics and attendance data to `.xlsx`
- **Swagger UI** — interactive API docs available at `/swagger-ui.html`
- **File upload and download** *(in progress)* — management of files
- **Basic auditing**  *(in progress)* — basic auditing

---

## Tech stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3 |
| Security | Spring Security + JWT (jjwt) |
| Persistence | Spring Data JPA + Hibernate |
| Database | MariaDB |
| File generation | Apache POI (Excel) · iText / JasperReports (PDF) |
| API docs | SpringDoc OpenAPI / Swagger UI |
| Containerization | Docker + Docker Compose |
| Build tool | Maven |

---

## Architecture

The project follows a standard layered architecture:

```
Controller  →  Service  →  Repository  →  Database
                ↓
           DTOs / Mappers
                ↓
         File generators (PDF, Excel)
```

- **Controllers** handle HTTP requests and delegate to services. No business logic lives here.
- **Services** contain all business rules and orchestrate repositories.
- **Repositories** are Spring Data JPA interfaces — no boilerplate SQL.
- **DTOs** separate the API contract from the persistence model.
- **Security** is applied at the controller layer via Spring Security filter chain; JWT is validated on every protected request.

---

## Getting started

### Prerequisites

- Java 21+
- Maven 3.9+
- MariaDB 10.6+ (or Docker)



---

## API reference

Full interactive docs available at `/swagger-ui.html` when the app is running.

### Authentication

| Method | Endpoint | Description | Auth required |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user | No |
| `POST` | `/api/auth/login` | Login and receive JWT | No |

**Login response example:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "username": "brenda@example.com",
  "roles": ["ROLE_ADMIN"]
}
```

Include the token in subsequent requests:
```
Authorization: Bearer <token>
```

---
# Author

**Brenda Landa**  
Systems Engineer · M.Sc. in Applied Computing  
Backend Developer — Java · Spring Boot

[![GitHub](https://img.shields.io/badge/GitHub-BrendaALandaC-181717?style=flat-square&logo=github)](https://github.com/BrendaALandaC)

---

*This project is part of a backend development portfolio. It is based on a real system built in my previous job.