package com.jitesh.taskmanager.gui;

import com.jitesh.taskmanager.Task;
import com.jitesh.taskmanager.JsonStorage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManagerGUI extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> taskList;
    private JTextField taskInput;
    private JButton addButton, completeButton, deleteButton;
    private List<Task> tasks;
    private JsonStorage storage;

    public TaskManagerGUI() {
        storage = new JsonStorage();
        tasks = storage.loadTasks();
        if (tasks == null) tasks = new ArrayList<>();

        initializeUI();
        refreshTaskList();
    }

    private void initializeUI() {
        setTitle("Task Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(45, 45, 45));

        // Header
        JLabel header = new JLabel("📋 Task Manager", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(new Color(100, 200, 255));
        mainPanel.add(header, BorderLayout.NORTH);

        // Task list
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskList.setBackground(new Color(60, 60, 60));
        taskList.setForeground(Color.WHITE);
        taskList.setSelectionBackground(new Color(100, 150, 200));
        taskList.setFixedCellHeight(30);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Bottom panel - input and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(new Color(45, 45, 45));

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        inputPanel.setBackground(new Color(45, 45, 45));

        taskInput = new JTextField();
        taskInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskInput.setBackground(new Color(60, 60, 60));
        taskInput.setForeground(Color.WHITE);
        taskInput.setCaretColor(Color.WHITE);
        taskInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        addButton = createStyledButton("Add Task", new Color(76, 175, 80));
        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(45, 45, 45));

        completeButton = createStyledButton("✓ Mark Complete", new Color(33, 150, 243));
        deleteButton = createStyledButton("✗ Delete", new Color(244, 67, 54));

        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);

        bottomPanel.add(inputPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Event listeners
        addButton.addActionListener(e -> addTask());
        taskInput.addActionListener(e -> addTask());
        completeButton.addActionListener(e -> markComplete());
        deleteButton.addActionListener(e -> deleteTask());
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void addTask() {
        String title = taskInput.getText().trim();
        if (!title.isEmpty()) {
            tasks.add(new Task(title));
            storage.saveTasks(tasks);
            refreshTaskList();
            taskInput.setText("");
        }
    }

    private void markComplete() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            tasks.get(index).setCompleted(true);
            storage.saveTasks(tasks);
            refreshTaskList();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task first!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int index = taskList.getSelectedIndex();
        if (index >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this task?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tasks.remove(index);
                storage.saveTasks(tasks);
                refreshTaskList();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task first!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshTaskList() {
        listModel.clear();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String status = task.isCompleted() ? "✓" : "○";
            listModel.addElement((i + 1) + ". " + status + " " + task.getTitle());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TaskManagerGUI().setVisible(true);
        });
    }
}
