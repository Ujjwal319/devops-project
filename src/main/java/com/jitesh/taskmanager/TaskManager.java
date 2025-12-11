package com.jitesh.taskmanager;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    private List<Task> tasks;

    public TaskManager() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(String title) {
        Task task = new Task(title);
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void markComplete(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).setCompleted(true);
        }
    }

    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No tasks found." + ConsoleColors.RESET);
            return;
        }
        System.out.println(ConsoleColors.BOLD + "\n--- Your Tasks ---" + ConsoleColors.RESET);
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String color = task.isCompleted() ? ConsoleColors.GREEN : ConsoleColors.WHITE;
            String status = task.isCompleted() ? "[✓]" : "[ ]";
            System.out.println(color + (i + 1) + ". " + status + " " + task.getTitle() + ConsoleColors.RESET);
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }
}
