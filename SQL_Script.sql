-- ========================================================
-- DATABASE SETUP & INITIALIZATION FOR SCMS
-- ========================================================

CREATE DATABASE IF NOT EXISTS scms_db;
USE scms_db;

-- Drop existing tables in reverse dependency order
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS enrollments;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS instructors;
DROP TABLE IF EXISTS students;

-- --------------------------------------------------------
-- Table: users
-- --------------------------------------------------------
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'STAFF')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table: instructors
-- --------------------------------------------------------
CREATE TABLE instructors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    instructor_code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    designation VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table: students
-- --------------------------------------------------------
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_code VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    gender VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table: courses
-- --------------------------------------------------------
CREATE TABLE courses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(150) NOT NULL,
    course_code VARCHAR(50) NOT NULL UNIQUE,
    department VARCHAR(100) NOT NULL,
    semester VARCHAR(50) NOT NULL,
    credit_hours INT NOT NULL,
    instructor_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_courses_instructor FOREIGN KEY (instructor_id) 
        REFERENCES instructors(id) 
        ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Table: enrollments
-- --------------------------------------------------------
CREATE TABLE enrollments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrollment_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_enrollments_student FOREIGN KEY (student_id) 
        REFERENCES students(id) 
        ON DELETE CASCADE,
    CONSTRAINT fk_enrollments_course FOREIGN KEY (course_id) 
        REFERENCES courses(id) 
        ON DELETE CASCADE,
    CONSTRAINT uq_student_course UNIQUE (student_id, course_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ========================================================
-- SAMPLE DATA INSERTION (FOR TESTING / AUTH)
-- ========================================================

-- Users (BCrypt encoded password for "password123")
INSERT INTO users (username, password, role) VALUES
('admin', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1m1/g2Dq32pGkR4u2e7sQ8N2E4XzKye', 'ADMIN'),
('staff_user', '$2a$10$e0MYzXyjpJS7Pd0RVvHwHe1m1/g2Dq32pGkR4u2e7sQ8N2E4XzKye', 'STAFF');

-- Instructors
INSERT INTO instructors (instructor_code, name, email, department, designation) VALUES
('INS101', 'Dr. Alan Turing', 'alan.turing@example.com', 'Computer Science', 'Professor'),
('INS102', 'Dr. Ada Lovelace', 'ada.lovelace@example.com', 'Mathematics', 'Associate Professor'),
('INS103', 'Dr. Grace Hopper', 'grace.hopper@example.com', 'Computer Science', 'Assistant Professor');

-- Students
INSERT INTO students (student_code, first_name, last_name, email, phone, gender, status) VALUES
('STU001', 'John', 'Doe', 'john.doe@example.com', '1234567890', 'MALE', 'ACTIVE'),
('STU002', 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', 'FEMALE', 'ACTIVE'),
('STU003', 'Bob', 'Johnson', 'bob.johnson@example.com', '5551234567', 'MALE', 'INACTIVE');

-- Courses
INSERT INTO courses (course_name, course_code, department, semester, credit_hours, instructor_id) VALUES
('Data Structures and Algorithms', 'CS201', 'Computer Science', 'FALL_2026', 3, 1),
('Linear Algebra', 'MATH101', 'Mathematics', 'FALL_2026', 4, 2),
('Database Management Systems', 'CS301', 'Computer Science', 'SPRING_2026', 3, 3);

-- Enrollments
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2026-08-01'),
(1, 2, '2026-08-02'),
(2, 1, '2026-08-03'),
(3, 3, '2026-08-04');