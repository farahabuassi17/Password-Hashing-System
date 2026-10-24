# Password Hashing System

## Project Description

This project is a simple user authentication system developed in Java for the Software Security course (SDEV 4303), Chapter 3 – User Authentication.

The system allows users to register and log in securely using SHA-256 password hashing with a randomly generated salt.

## Features

- User registration with a username and password.
- Random salt generation for each user.
- Password hashing using SHA-256.
- Storage of usernames, salts, and hashed passwords in a text file.
- User login and password verification.
- Handling incorrect passwords and duplicate usernames.

## Technologies Used

- Java
- Apache NetBeans IDE
- SHA-256
- SecureRandom
- Text File Storage

## How the System Works

### 1. Registration

The user enters a username and password. The system generates a random salt, combines it with the password, and calculates a SHA-256 hash.

The username, salt, and hashed password are then stored in `users.txt`. The original password is not stored.

### 2. Login

The user enters their username and password. The system retrieves the stored salt and hash, calculates a new hash using the entered password and stored salt, and compares the results.

If the hashes match, login is successful. Otherwise, access is denied.

## How to Run

1. Install Java JDK.
2. Open Apache NetBeans.
3. Open the `PasswordHashingSystem` project.
4. Run `PasswordHashingSystem.java`.
5. Choose Register, Login, or Exit from the menu.

## Security Note

This project uses salted SHA-256 hashing for educational purposes, as required by the assignment. Real-world applications should use dedicated password-hashing algorithms such as Argon2id, bcrypt, or PBKDF2.

## Course Information

- **Course:** Software Security (SDEV 4303)
- **Chapter:** Chapter 3 – User Authentication
- **Instructor:** Dr. Abdelkareem Alashqar
- **Academic Year:** 2026/2027
