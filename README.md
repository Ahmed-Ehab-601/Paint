# Paint Application

## Overview
A simple paint app built with Spring Boot (backend) and React (frontend). Users can draw, edit, save/load shapes, and undo/redo actions.

## Demo Video
[![Watch the video](https://img.youtube.com/vi/noDJoOTozuQ/maxresdefault.jpg)](https://youtu.be/noDJoOTozuQ?si=DNnyBE6PRa3SYinN)

## Features
- Draw and edit shapes (border, color, opacity).
- Save/load drawings (XML/JSON).
- Undo/redo actions.
- Delete shapes (individual or all).

## Technologies
- **Backend**: Spring Boot (Java)
- **Frontend**: React (JavaScript)
- **Database**: PostgreSQL (or H2 in-memory)

## How to Run

### Backend:
1. Navigate to `paint/paintapp`.
2. Set up PostgreSQL (or use H2 by copying `recovery.txt` to `application.properties`).
3. Run the following commands:
    ```bash
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

### Frontend:
1. Navigate to `paint/frontend`.
2. Install dependencies:
    ```bash
    npm install
    ```
3. Run the following command:
    ```bash
    npm run dev
    ```

4. Access the frontend at: [http://localhost:5173](http://localhost:5173)

## Design Patterns
- **Factory**: Creates objects (shapes, commands).
- **Singleton**: Ensures single instance of classes.
- **Command**: Enables undo/redo actions.
- **Prototype**: Clones shapes.

## Assumptions
- Undo/redo is not available after loading drawings.
- Page reload clears all shapes.


 
