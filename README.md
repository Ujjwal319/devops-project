# Task Manager

A task manager application built with Java and Maven. Available in both CLI and GUI versions.

## Features
- Add, list, complete, and delete tasks
- Persistent storage using JSON
- Color coded CLI output with ANSI colors
- Modern dark-themed GUI using FlatLaf
- Checkbox-based task completion in GUI
- Shared data between CLI and GUI versions

## Tech Stack
- Java 11+
- Maven
- Gson (JSON handling)
- FlatLaf (Modern UI)
- JUnit 5 (Testing)

## How to Build
```bash
mvn clean package
```

## How to Run

### CLI Version
```bash
java -jar target/taskmanager-1.0-SNAPSHOT.jar
```

### GUI Version
```bash
java -cp target/taskmanager-1.0-SNAPSHOT.jar com.jitesh.taskmanager.gui.TaskManagerGUI
```

## Run Tests
```bash
mvn test
```

## Screenshots

### CLI
```
========= Task Manager =========
1. Add Task
2. List Tasks
3. Mark Complete
4. Delete Task
5. Exit
```

### GUI
Modern dark-themed interface with:
- Card-based task list
- Checkbox for marking complete
- One-click delete button
- Real-time updates
