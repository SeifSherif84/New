package com.example.se2project.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderRequest {
    private LocalDate reminderDate;
    private LocalTime reminderTime;

    // Getters and Setters
    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}