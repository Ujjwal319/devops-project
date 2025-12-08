package com.jitesh.taskmanager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        JsonStorage storage = new JsonStorage();
        Scanner scanner = new Scanner(System.in);

        // Load existing tasks
        manager.setTasks(storage.loadTasks());

        boolean running = true;
        while (running) {
            System.out.println("\n=== Task Manager ===");
            System.out.println("1. Add Task");
            System.out.println("2. List Tasks");
            System.out.println("3. Mark Complete");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();
                    manager.addTask(title);
                    storage.saveTasks(manager.getTasks());
                    System.out.println("Task added!");
                    break;
                case "2":
                    manager.listTasks();
                    break;
                case "3":
                    System.out.print("Enter task number: ");
                    try {
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        manager.markComplete(index);
                        storage.saveTasks(manager.getTasks());
                        System.out.println("Done!");
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number");
                    }
                    break;
                case "4":
                    running = false;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        scanner.close();
    }
}
