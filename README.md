# Quiz Application

## Description
## Overview

QuizMaster is a Spring Boot API designed for managing questions
and quizzes. This application provides a backend service that
stores questions and quizzes in a database, performs CRUD
operations, and works through HTTP requests.
Users can solve quizzes and receive their scores instantly.
While the API does not yet have a frontend, plans are underway
to determine the best approach for implementation.

## To-Do List

- [x] **QuestionModel:** Create a Question class to map with the database
- [x] **QuestionDAO:** Create CRUD operations for Question
- [x] **QuestionController:** Create a controller for Question
- [x] **QuestionService:** Create a service class for business logic and exception handling

- [x] **QuizModel:** Create a Quiz class to map with the database
- [x] **QuizDAO:** Create CRUD operations for Quiz
- [x] **QuizController:** Create a controller for Quiz
- [x] **QuizService:** Create a service class for business logic and exception handling

- [x] **QuestionWithoutAnswer:** Create a DTO class to manage getting question without answers for quizzes
- [x] **QuestionResponse:** Create DTO class to manage quiz solving and scores

- [ ] **Front-end:** Create a front-end layer

## Features

1. **Question Management**
   - **Create Questions**: Create questions with up to 4 options and one answer.
   - **Edit Questions**: Modify existing questions.
   - **Delete Questions**: Remove outdated or incorrect questions from the database.

2. **Quiz Management**
   - **Create Quizzes**: Compile a set of questions into a cohesive quiz.
   - **Edit Quizzes**: Add and remove questions from quizzes.
   - **Delete Quizzes**: Remove obsolete quizzes from the system.

3. **Quiz Participation**
   - **Solve Quizzes**: Users can take quizzes and submit their answers through an API.
   - **Instant Feedback**: Receive immediate scoring and feedback on quiz performance.

## Technology Stack

- **Spring Boot**: Provides a robust and scalable framework for developing the application.
- **Spring Data JPA**: Manages database interactions seamlessly.
- **PostgreSQL**: Serves as the primary database for storing questions, quizzes, and user data.

## Installation and Running
1. **Clone the repository:**

   ```sh
   git clone https://github.com/Hayk-Gevorgyan/quizApplication.git
2. **Change database properties in [application.properties](src/main/resources/application.properties) file**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/quizDB
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.datasource.username=postgres
   spring.datasource.password=root
3. **Open the terminal from the projects directory**
4. **Run this commands**
   ```sh
   mvn clean package
   java -jar target\quiz-0.0.1-SNAPSHOT.jar
5. **To terminate the program press**
   Ctrl+C

## Usage

### Question HTTP Requests

#### GET Mappings

- **/question/allQuestions**: Gets all questions from the database.
- **/question/get/{id}**: Gets a question by `id`.

#### POST Mappings

- **/question/create**: Creates a question using the passed request body and returns the `id` of the new question.
- **/question/createMultiple**: Creates multiple questions using the passed request body as a list of `Questions`.

#### UPDATE Mappings

- **/question/update**: Updates the question with the same `id` as the request body `question` to match the request body.
- **/question/update/{id}**: Updates the question with the specified path variable `id` to match the request body.

#### DELETE Mappings

- **/question/delete/{id}**: Deletes the question with the specified path variable `id`.
- **/question/delete**: Deletes the question specified in the request body.
- **/question/deleteAll**: Deletes all the questions.

### Quiz Mappings

#### GET Requests

- **/quiz/get**: Takes two parameters, `id` and `title`. Returns the quiz by `id` if only `id` is passed, or returns the quiz whose title matches the passed `title`, and returns the quiz by `title` if only the `title` is passed.

#### PUT Requests

- **/quiz/update**: Takes a request body `quiz` and updates the quiz with the same ID if it exists; otherwise, does nothing.

#### PUT Requests for Adding and Deleting Questions

- **/quiz/addQuestion/{quizId}**: Takes a request parameter of the `questionId` to add and adds it to the quiz with the path variable `quizId`.

- **/quiz/deleteQuestion/{quizId}**: Takes a request parameter of the `questionId` to delete from the quiz with the path variable `quizId`.

#### POST Request

- **/quiz/solve/{quizId}**: Takes a request body as a list of `QuestionResponses` and returns the score for the quiz with the specified `quizId`.

#### DELETE Request

- **/quiz/delete/{quizId}**: Deletes the quiz with the specified `quizId`.

