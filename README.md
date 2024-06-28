# Quiz Application

## Description
This is an application that supports CRUD 
operations with questions, connectivity with a 
PostgreSQL database, sends and receives http 
requests.

## To-Do List

- [ ] **QuizModel:** Create a Quiz class to map with the database
- [ ] **QuizDAO:** Create CRUD operations for Quiz
- [ ] **QuizController:** Create a controller for Quiz
- [ ] **QuizService:** Create a service class for business logic and exception handling
- [ ] **Front-end:** Create a front-end layer

## Installation and Running
1. **Clone the repository:**

   ```sh
   git clone https://github.com/Hayk-Gevorgyan/quizApplication.git
2. **Open the terminal from the projects directory**
3. **Run this commands**
   ```sh
   mvn clean package
   java -jar target\quiz-0.0.1-SNAPSHOT.jar
4. **To terminate the program press**
   Ctrl+C

## Usage
Send requests to localhost:8080 and receive responds
