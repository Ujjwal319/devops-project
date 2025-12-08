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
}
