# Virtual Casino

**Virtual Casino** is a Java-based desktop application developed as an integrative project for a higher vocational training program in web development.

---

## 📚 Table of Contents
- [🧾 Project Overview](#-project-overview "Learn more about the game's concept and purpose")
- [🏗️ Architecture](#-architecture "See the general structure and design of the project")
- [🚀 How to Run](#-how-to-run "Instructions to install and run the project locally")
- [🕹️ Using the Application](#-using-the-application "Step-by-step guide on how to play or use the game")
- [⚙️ Technologies Used](#-technologies-used "See which technologies were used to build the project")

## 🧾 Project Overview

The main goal was to design and implement a CRUD system connected to a MySQL database with a visual interface built using Java Swing.

This project simulates a small casino management system, allowing users to register, log in, manage clients and games, and play simple casino games like Blackjack and Slot Machine.  

---

## 🏗️ Architecture

The application follows the **MVC pattern** (Model, View, Controller), containing contains the following packages:

- ### 📦 controller
The package responsible for performing most logical operations and connecting the view to the database.

| Class | Description |
|-------|--------------|
| **DataBaseController** | Manages database operations across different DAOs (`UserDAO`, `ClientDAO`, `GameDAO`, `GameSessionDAO`). |
| **Launcher** | The main entry point of the application. |
| **MainController** | Central controller coordinating between the Model (game logic, clients, users) and the View (UI). |
| **Validator** | Validates input and output data across the application. |
| **ViewController** | Manages user interfaces and handles window or panel transitions. |

---

- ### 📦 dao
The package responsible for sending queries to the database

| Class | Description |
|-------|--------------|
| **ClientDAO** | Manages CRUD operations for `Client` entities. |
| **DataBaseConnector** | Handles the connection between the application and the MySQL database. |
| **DatabaseManager** | Central manager for all database operations within the MVC structure. |
| **GameDAO** | Manages CRUD operations for `Game` entities. |
| **GameSessionDAO** | Handles persistence of game session data. |
| **UserDAO** | Manages CRUD operations for `User` entities. |

---

- ### 📦 model
The package responsible for defining the data received from the database 

| Class | Description |
|-------|--------------|
| **Blackjack** | Represents the Blackjack game (extends `Game`). |
| **Client** | Represents a client (customer) in the casino system. |
| **Game** | Abstract base class representing a casino game. |
| **Session** | Manages user login sessions in the application. |
| **Slotmachine** | Represents the Slot Machine game (extends `Game`). |
| **User** | Represents a user profile in the system. |

---

- ### 📦 ui
The package that provides the user interface for the application

| Class | Description |
|-------|--------------|
| **BlackjackUI** | Panel where the Blackjack game is played. |
| **ClientEditUI** | Panel for editing existing client data. |
| **ClientUI** | Panel for adding new clients to the system. |
| **ConnectUI** | Login or registration panel. |
| **GameEditUI** | Panel for editing existing games. |
| **GameUI** | Panel for adding new games. |
| **HomeUI** | Main menu panel of the casino simulator. |
| **LogInUI** | Login panel for existing users. |
| **MainFrame** | The main frame containing and managing all panels. |
| **ManagementUI** | Panel for managing clients and games. |
| **PlayUI** | Panel for launching games. |
| **ProfileUI** | Displays information about the currently logged-in user. |
| **SignInUI** | Allows creation of a new account. |
| **SlotmachineUI** | Panel where the Slot Machine game is played. |
| **StatsUI** | Displays user-specific game statistics. |

- ### 📦 Exceptions
The package responsible using custom exceptions and hadling them 

| Class | Description |
|--------|-------------|
| **BetException** | Custom exception thrown when a bet cannot be placed due to various reasons. |
| **ExceptionMessage** | Utility class to handle exceptions by displaying a user-friendly error message and logging technical details to the console. |
| **GameException** | Exception thrown when an attempt is made to open the games window without any existing user or game created beforehand. |
| **MailException** | Exception thrown when an entered email is invalid or does not meet the expected format. |

---

## 🚀 How to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/virtual-casino.git
   ```

   ---

## ⚙️ Technologies Used

- **Java 17+**
- **Swing (Javax Swing)** — for building the graphical interface.
- **MySQL** — as the relational database.
- **JDBC** — for database communication.
- **MVC Architecture** — for clean separation of logic, data, and UI.
- **DAO Pattern** — for structured and reusable database access.

## 🕹️ Using the Application
