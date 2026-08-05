# Student & Course Management System (SCMS)

A robust backend RESTful API service built with **Spring Boot 3**, **Java 25**, and **MySQL**. The system facilitates administrative management of students, instructors, courses, and enrollments with dynamic filtering, pagination, sorting, and JWT-based authentication.

---

## 📌 Project Overview

The **Student & Course Management System (SCMS)** provides end-to-end API management for educational institutions. Key capabilities include:

* **User Authentication & Authorization:** Role-based access control (`ADMIN`, `STAFF`) using Spring Security and JWT tokens.
* **Student Management:** Full CRUD operations and multi-parameter search for student records.
* **Instructor Management:** Manage academic personnel across departments and designations.
* **Course Management:** Course creation linked with designated instructors, department mapping, and credit hours.
* **Enrollment System:** Track student course enrollments with relational constraints and date filtering.
* **Advanced Criteria Search:** Dynamic, individual-parameter AND-filtering using **Spring Data JPA Specifications**.
* **Database Resilience:** Fully relational database schema enforced with foreign key constraints in **MySQL**.

---

## 🛠️ Technology Stack

* **Language:** Java 25
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security, JWT (JSON Web Tokens)
* **ORM / Database Access:** Spring Data JPA, Hibernate, Criteria API
* **Database:** MySQL 8.x
* **Build Tool:** Apache Maven
* **Utilities & Testing:** Lombok, JUnit 5, Mockito, AssertJ

---

## 🚀 Setup Instructions

### Prerequisites
Make sure you have the following installed on your machine:
* **Java Development Kit (JDK) 25** or JDK 17+
* **Apache Maven** 3.8+
* **MySQL Server** 8.0+
* **Postman** (for API testing)

### Installation Steps

1. **Clone the Repository**
   ```bash
   git clone [https://github.com/your-username/SCMS.git](https://github.com/your-username/SCMS.git)
   cd SCMS
