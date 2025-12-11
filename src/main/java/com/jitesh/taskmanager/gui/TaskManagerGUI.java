package com.jitesh.taskmanager.gui;

import com.jitesh.taskmanager.Task;
import com.jitesh.taskmanager.JsonStorage;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManagerGUI extends JFrame {
    private JPanel taskListPanel;
    private JTextField taskInput;
    private List<Task> tasks;
    private JsonStorage storage;

    private static final Color BG_DARK = new Color(30, 32, 34);
    private static final Color BG_CARD = new Color(42, 44, 46);
    private static final Color ACCENT_GREEN = new Color(46, 204, 113);
    private static final Color ACCENT_RED = new Color(231, 76, 60);
    private static final Color TEXT_PRIMARY = new Color(236, 240, 241);
    private static final Color TEXT_MUTED = new Color(149, 165, 166);

    public TaskManagerGUI() {
        storage = new JsonStorage();
        tasks = storage.loadTasks();
        if (tasks == null) tasks = new ArrayList<>();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Task Manager");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(BG_DARK);

        // Header
        JLabel header = new JLabel("Task Manager");
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(TEXT_PRIMARY);
        mainPanel.add(header, BorderLayout.NORTH);

        // Task list with scroll
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        taskListPanel.setBackground(BG_DARK);

        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG_DARK);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel at bottom
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setBackground(BG_DARK);

        taskInput = new JTextField();
        taskInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskInput.setBackground(BG_CARD);
        taskInput.setForeground(TEXT_PRIMARY);
        taskInput.setCaretColor(TEXT_PRIMARY);
        taskInput.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 62, 64)),
            new EmptyBorder(12, 15, 12, 15)
        ));
        taskInput.addActionListener(e -> addTask());

        JButton addBtn = createButton("+ Add Task", ACCENT_GREEN);
        addBtn.addActionListener(e -> addTask());

        inputPanel.add(taskInput, BorderLayout.CENTER);
        inputPanel.add(addBtn, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
        refreshTaskList();
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        return btn;
    }

    private void addTask() {
        String title = taskInput.getText().trim();
        if (!title.isEmpty()) {
            tasks.add(new Task(title));
            storage.saveTasks(tasks);
            taskInput.setText("");
            refreshTaskList();
        }
    }

    private void refreshTaskList() {
        taskListPanel.removeAll();
        
        if (tasks.isEmpty()) {
            JLabel empty = new JLabel("No tasks yet. Add one above!");
            empty.setForeground(TEXT_MUTED);
            empty.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            empty.setAlignmentX(Component.CENTER_ALIGNMENT);
            empty.setBorder(new EmptyBorder(50, 0, 0, 0));
            taskListPanel.add(empty);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                taskListPanel.add(createTaskCard(tasks.get(i), i));
                taskListPanel.add(Box.createVerticalStrut(8));
            }
        }
        
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private JPanel createTaskCard(Task task, int index) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(BG_CARD);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Checkbox
        JCheckBox checkBox = new JCheckBox();
        checkBox.setSelected(task.isCompleted());
        checkBox.setBackground(BG_CARD);
        checkBox.setFocusPainted(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(e -> {
            task.setCompleted(checkBox.isSelected());
            storage.saveTasks(tasks);
            refreshTaskList();
        });

        // Title
        JLabel titleLabel = new JLabel(task.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (task.isCompleted()) {
            titleLabel.setText("<html><strike>" + task.getTitle() + "</strike></html>");
            titleLabel.setForeground(TEXT_MUTED);
        } else {
            titleLabel.setForeground(TEXT_PRIMARY);
        }

        // Delete button
        JButton deleteBtn = new JButton("X");
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        deleteBtn.setBackground(ACCENT_RED);
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFocusPainted(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.setPreferredSize(new Dimension(35, 35));
        deleteBtn.addActionListener(e -> {
            tasks.remove(index);
            storage.saveTasks(tasks);
            refreshTaskList();
        });

        card.add(checkBox, BorderLayout.WEST);
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(deleteBtn, BorderLayout.EAST);

        return card;
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new TaskManagerGUI().setVisible(true);
        });
    }
}
