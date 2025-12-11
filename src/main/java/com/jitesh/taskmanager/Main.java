package com.jitesh.taskmanager;

import java.util.Scanner;
import static com.jitesh.taskmanager.ConsoleColors.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        JsonStorage storage = new JsonStorage();
        Scanner scanner = new Scanner(System.in);

        // Load existing tasks
        manager.setTasks(storage.loadTasks());

        boolean running = true;
        while (running) {
            System.out.println("\n" + BOLD_CYAN + "========= Task Manager =========" + RESET);
            System.out.println(YELLOW + "1." + RESET + " Add Task");
            System.out.println(YELLOW + "2." + RESET + " List Tasks");
            System.out.println(YELLOW + "3." + RESET + " Mark Complete");
            System.out.println(YELLOW + "4." + RESET + " Delete Task");
            System.out.println(YELLOW + "5." + RESET + " Exit");
            System.out.print(CYAN + "Choose option: " + RESET);

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print(CYAN + "Enter task title: " + RESET);
                    String title = scanner.nextLine();
                    manager.addTask(title);
                    storage.saveTasks(manager.getTasks());
                    System.out.println(GREEN + "✓ Task added!" + RESET);
                    break;
                case "2":
                    manager.listTasks();
                    break;
                case "3":
                    manager.listTasks();
                    System.out.print(CYAN + "Enter task number to complete: " + RESET);
                    try {
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        manager.markComplete(index);
                        storage.saveTasks(manager.getTasks());
                        System.out.println(GREEN + "✓ Task marked complete!" + RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "✗ Invalid number" + RESET);
                    }
                    break;
                case "4":
                    manager.listTasks();
                    System.out.print(CYAN + "Enter task number to delete: " + RESET);
                    try {
                        int delIndex = Integer.parseInt(scanner.nextLine()) - 1;
                        manager.deleteTask(delIndex);
                        storage.saveTasks(manager.getTasks());
                        System.out.println(GREEN + "✓ Task deleted!" + RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(RED + "✗ Invalid number" + RESET);
                    }
                    break;
                case "5":
                    running = false;
                    System.out.println(BOLD_GREEN + "Goodbye! Thanks for using Task Manager" + RESET);
                    break;
                default:
                    System.out.println(RED + "✗ Invalid option" + RESET);
            }
        }
        scanner.close();
    }
}
