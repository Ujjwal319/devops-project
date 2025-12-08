package com.jitesh.taskmanager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {
    private static final String FILE_NAME = "tasks.json";
    private Gson gson;

    public JsonStorage() {
        this.gson = new Gson();
    }

    public void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks");
        }
    }

    public List<Task> loadTasks() {
        try (FileReader reader = new FileReader(FILE_NAME)) {
            Type taskListType = new TypeToken<ArrayList<Task>>(){}.getType();
            return gson.fromJson(reader, taskListType);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
