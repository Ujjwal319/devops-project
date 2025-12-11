# Task Manager

A task manager application built with Java and Maven. Available in both CLI and GUI versions.

## Features
- Add tasks
- List tasks
- Mark complete
- Delete tasks
- Color coded CLI output
- Modern dark-themed GUI

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
