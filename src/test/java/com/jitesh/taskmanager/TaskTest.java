package com.jitesh.taskmanager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void testTaskCreation() {
        Task task = new Task("Buy groceries");
        assertEquals("Buy groceries", task.getTitle());
        assertFalse(task.isCompleted());
    }

    @Test
    public void testMarkComplete() {
        Task task = new Task("Study Java");
        task.setCompleted(true);
        assertTrue(task.isCompleted());
    }

    @Test
    public void testToString() {
        Task task = new Task("Test task");
        assertEquals("[ ] Test task", task.toString());
        
        task.setCompleted(true);
        assertEquals("[X] Test task", task.toString());
    }
}
