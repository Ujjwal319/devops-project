# Task Manager - Project Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Project Structure](#project-structure)
4. [Implementation Details](#implementation-details)
5. [GUI Implementation](#gui-implementation)
6. [Git Workflow](#git-workflow)
7. [How to Run](#how-to-run)
8. [Screenshots](#screenshots)

---

## Project Overview

**Task Manager** is a task management application built using Java, available in both CLI and GUI versions. It allows users to manage their daily tasks through an interactive interface. Tasks are persisted in a JSON file, ensuring data is saved between sessions.

### Key Features
- ✅ Add new tasks
- ✅ List all tasks with completion status
- ✅ Mark tasks as completed
- ✅ Delete tasks
- ✅ Persistent storage using JSON
- ✅ Color-coded CLI output with ANSI colors
- ✅ Modern dark-themed GUI using FlatLaf
- ✅ Checkbox-based task completion in GUI
- ✅ Shared data between CLI and GUI versions

---

## Technology Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | JDK 11+ | Core programming language |
| **Maven** | 3.9.x | Build automation & dependency management |
| **Gson** | 2.10.1 | JSON serialization/deserialization |
| **FlatLaf** | 3.2.5 | Modern Look and Feel for Swing GUI |
| **JUnit 5** | 5.9.2 | Unit testing framework |
| **Git** | Latest | Version control |
| **GitHub** | - | Remote repository hosting |

### Why These Tools?

**Java**: Platform-independent, object-oriented language perfect for building robust applications.

**Maven**: Simplifies build process, manages dependencies automatically, and creates executable JAR files.

**Gson**: Google's library for converting Java objects to JSON and vice versa. Easy to use and lightweight.

**FlatLaf**: Modern flat Look and Feel for Java Swing applications. Provides dark theme and modern UI components.

**JUnit 5**: Industry-standard testing framework for Java applications.

**Git**: Essential for version control, collaboration, and tracking project history.

---

## Project Structure

```
taskmanager/
├── pom.xml                          # Maven configuration file
├── README.md                        # Project readme
├── SYNOPSIS.md                      # Project synopsis
├── .gitignore                       # Git ignore rules
├── tasks.json                       # Task data file (generated at runtime)
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── jitesh/
│   │               └── taskmanager/
│   │                   ├── Main.java           # CLI entry point
│   │                   ├── Task.java           # Task model class
│   │                   ├── TaskManager.java    # Business logic
│   │                   ├── JsonStorage.java    # JSON file operations
│   │                   ├── ConsoleColors.java  # ANSI color codes for CLI
│   │                   └── gui/
│   │                       └── TaskManagerGUI.java  # GUI version
│   └── test/
│       └── java/
│           └── com/
│               └── jitesh/
│                   └── taskmanager/
│                       └── TaskTest.java       # Unit tests
└── target/                          # Build output (generated)
    └── taskmanager-1.0-SNAPSHOT.jar # Executable JAR
```

---

## Implementation Details

### 1. Task.java - The Model Class

```java
package com.jitesh.taskmanager;

public class Task {
    private String title;
    private boolean completed;
    
    // Constructor, getters, setters, toString()
}
```

**Purpose**: Represents a single task with:
- `title`: The task description
- `completed`: Boolean flag indicating completion status

**Key Method**:
```java
@Override
public String toString() {
    return "[" + (completed ? "X" : " ") + "] " + title;
}
```
This displays tasks as `[X] Task name` (completed) or `[ ] Task name` (pending).

---

### 2. TaskManager.java - Business Logic

```java
package com.jitesh.taskmanager;

public class TaskManager {
    private List<Task> tasks;
    
    public void addTask(String title) { ... }
    public void listTasks() { ... }
    public void markComplete(int index) { ... }
    public void deleteTask(int index) { ... }
}
```

**Purpose**: Manages the collection of tasks and provides CRUD operations.

| Method | Description |
|--------|-------------|
| `addTask(String title)` | Creates new task and adds to list |
| `listTasks()` | Displays all tasks with numbering |
| `markComplete(int index)` | Sets task's completed flag to true |
| `deleteTask(int index)` | Removes task from list |
| `getTasks()` | Returns task list for saving |
| `setTasks(List<Task>)` | Loads tasks from storage |

---

### 3. JsonStorage.java - Persistence Layer

```java
package com.jitesh.taskmanager;

public class JsonStorage {
    private static final String FILE_NAME = "tasks.json";
    private Gson gson;
    
    public void saveTasks(List<Task> tasks) { ... }
    public List<Task> loadTasks() { ... }
}
```

**Purpose**: Handles reading and writing tasks to JSON file.

**Save Operation**:
```java
public void saveTasks(List<Task> tasks) {
    try (FileWriter writer = new FileWriter(FILE_NAME)) {
        gson.toJson(tasks, writer);
    } catch (IOException e) {
        System.out.println("Error saving tasks");
    }
}
```

**Load Operation**:
```java
public List<Task> loadTasks() {
    File file = new File(FILE_NAME);
    if (!file.exists()) {
        return new ArrayList<>();
    }
    // Read and parse JSON file
}
```

**Sample tasks.json**:
```json
[
  {"title": "Complete Java project", "completed": true},
  {"title": "Study for exams", "completed": false},
  {"title": "Submit assignment", "completed": false}
]
```

---

### 4. Main.java - Entry Point & CLI Menu

```java
package com.jitesh.taskmanager;

public class Main {
    public static void main(String[] args) {
        // Initialize components
        // Display menu loop
        // Handle user input
    }
}
```

**Menu Flow**:
```
========= Task Manager =========
1. Add Task
2. List Tasks
3. Mark Complete
4. Delete Task
5. Exit
Choose option: _
```

**Program Flow**:
1. Load existing tasks from JSON file
2. Display menu options
3. Read user choice
4. Execute corresponding operation
5. Save changes to JSON file
6. Repeat until user exits

---

### 5. TaskTest.java - Unit Tests

```java
package com.jitesh.taskmanager;

public class TaskTest {
    @Test
    public void testTaskCreation() { ... }
    
    @Test
    public void testMarkComplete() { ... }
    
    @Test
    public void testToString() { ... }
}
```

**Test Cases**:
| Test | Description |
|------|-------------|
| `testTaskCreation` | Verifies task is created with correct title and incomplete status |
| `testMarkComplete` | Verifies task can be marked as completed |
| `testToString` | Verifies correct string representation |

---

## GUI Implementation

### 6. TaskManagerGUI.java - Graphical Interface

```java
package com.jitesh.taskmanager.gui;

public class TaskManagerGUI extends JFrame {
    private JPanel taskListPanel;
    private JTextField taskInput;
    private List<Task> tasks;
    private JsonStorage storage;
    
    // Uses FlatLaf for modern dark theme
    // Card-based task display with checkboxes
}
```

**Purpose**: Provides a modern graphical interface for task management.

**Key Features**:
| Feature | Description |
|---------|-------------|
| Dark Theme | Uses FlatLaf library for modern dark UI |
| Card Layout | Each task displayed as a card |
| Checkboxes | Click to toggle task completion |
| Delete Button | Red X button for quick deletion |
| Real-time Updates | UI updates immediately on changes |
| Shared Storage | Uses same tasks.json as CLI version |

**UI Components**:
- Header with app title
- Scrollable task list panel
- Text input field for new tasks
- Add Task button (green)
- Per-task checkbox and delete button

**How to Run GUI**:
```bash
java -cp target/taskmanager-1.0-SNAPSHOT.jar com.jitesh.taskmanager.gui.TaskManagerGUI
```

---

### 7. ConsoleColors.java - CLI Color Support

```java
package com.jitesh.taskmanager;

public class ConsoleColors {
    public static final String RESET = "\033[0m";
    public static final String GREEN = "\033[0;32m";
    public static final String RED = "\033[0;31m";
    // ... more colors
}
```

**Purpose**: Provides ANSI color codes for colorful CLI output.

---

## Git Workflow

### Branches Used

| Branch | Purpose |
|--------|---------|
| `main` | Production-ready code |
| `dev` | Development branch |
| `feat/add-task` | Task addition feature |
| `feat/json-support` | JSON storage feature |
| `feat/cli-menu` | CLI menu feature |
| `feat/add-delete` | Delete functionality |
| `hotfix/typo-fix` | Quick fixes |

### Git Commands Used

```bash
# Initialize repository
git init

# Basic operations
git add .
git commit -m "message"
git status
git log --oneline

# Branching
git branch <branch-name>
git checkout <branch-name>
git checkout -b <new-branch>

# Merging
git merge <branch-name>

# Remote operations
git remote add origin <url>
git push -u origin main
git pull origin main

# Advanced operations
git stash -m "message"      # Save work temporarily
git stash pop               # Restore stashed work
git rebase main             # Rebase branch onto main
git cherry-pick <commit>    # Pick specific commit

# Conflict resolution
# Edit conflicted files
git add <resolved-file>
git commit -m "resolved conflict"
```

### Commit History Highlights

```
* Merge pull request #2 - hotfix/typo-fix
* improved exit message (cherry-picked)
* added delete option in menu
* added delete task method
* added toString test
* final readme update
* resolved merge conflict in readme
* added unit tests for Task class
* added junit and jar plugins
* Merge pull request #1 - feat/cli-menu
* small fix, ab chal raha hai
* fixed null pointer bug in loadTasks
* created JsonStorage for saving tasks
* added gson dependency
* forgot toString in Task
* created TaskManager class
* added Task class
* added gitignore
* initial commit
```

---

## How to Run

### Prerequisites
- Java JDK 11 or higher installed
- Maven 3.6+ installed
- Git installed

### Step 1: Clone the Repository
```bash
git clone https://github.com/Ujjwal319/devops-project.git
cd devops-project
```

### Step 2: Build the Project
```bash
mvn clean package
```
This will:
- Download dependencies (Gson, JUnit)
- Compile Java source files
- Run unit tests
- Create executable JAR in `target/` folder

### Step 3: Run the Application

**Option A - Using Maven:**
```bash
mvn exec:java -Dexec.mainClass="com.jitesh.taskmanager.Main"
```

**Option B - Using JAR file:**
```bash
java -jar target/taskmanager-1.0-SNAPSHOT.jar
```

**Option C - If JAVA_HOME not set (Windows):**
```bash
& "C:\Program Files\Java\jdk-25\bin\java.exe" -jar target/taskmanager-1.0-SNAPSHOT.jar
```

### Step 4: Run Tests
```bash
mvn test
```

---

## Screenshots

### Application Menu
```
========= Task Manager =========
1. Add Task
2. List Tasks
3. Mark Complete
4. Delete Task
5. Exit
Choose option: 
```

### Adding a Task
```
Choose option: 1
Enter task title: Complete Java assignment
Task added!
```

### Listing Tasks
```
Choose option: 2
1. [ ] Complete Java assignment
2. [ ] Study for exams
3. [X] Submit project
```

### Marking Complete
```
Choose option: 3
1. [ ] Complete Java assignment
2. [ ] Study for exams
Enter task number to complete: 1
Task marked complete!
```

### Deleting a Task
```
Choose option: 4
1. [X] Complete Java assignment
2. [ ] Study for exams
Enter task number to delete: 2
Task deleted!
```

### Exiting
```
Choose option: 5
Goodbye! Thanks for using Task Manager
```

---

## Maven Configuration (pom.xml)

```xml
<dependencies>
    <!-- JSON Processing -->
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
    
    <!-- Modern UI Look and Feel -->
    <dependency>
        <groupId>com.formdev</groupId>
        <artifactId>flatlaf</artifactId>
        <version>3.2.5</version>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.9.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <!-- JAR Plugin - Sets main class -->
        <plugin>
            <artifactId>maven-jar-plugin</artifactId>
            <configuration>
                <archive>
                    <manifest>
                        <mainClass>com.jitesh.taskmanager.Main</mainClass>
                    </manifest>
                </archive>
            </configuration>
        </plugin>
        
        <!-- Shade Plugin - Creates fat JAR with dependencies -->
        <plugin>
            <artifactId>maven-shade-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

---

## Conclusion

This project demonstrates:
- ✅ Object-Oriented Programming in Java
- ✅ File I/O operations with JSON
- ✅ Maven build automation
- ✅ Unit testing with JUnit 5
- ✅ Git version control with branching strategies
- ✅ GitHub collaboration with Pull Requests
- ✅ GUI development with Java Swing
- ✅ Modern UI theming with FlatLaf
- ✅ ANSI color codes for CLI enhancement

---

**Author**: Ujjwal  
**Repository**: https://github.com/Ujjwal319/devops-project  
**Date**: December 2025
