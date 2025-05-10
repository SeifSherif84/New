package com.example.se2project.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Notification {

    private String event_title;
    private LocalDate notification_Date;
    private LocalTime notification_Time;
    private LocalDate event_Date;
    private LocalTime event_Time;
    private String notification_description;

    public Notification(String event_title, LocalDate notification_Date, LocalTime notification_Time, LocalDate event_Date, LocalTime event_Time, String notification_description) {
        this.event_title = event_title;
        this.notification_Date = notification_Date;
        this.notification_Time = notification_Time;
        this.event_Date = event_Date;
        this.event_Time = event_Time;
        this.notification_description = notification_description;
    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public LocalDate getNotification_Date() {
        return notification_Date;
    }

    public void setNotification_Date(LocalDate notification_Date) {
        this.notification_Date = notification_Date;
    }

    public LocalTime getNotification_Time() {
        return notification_Time;
    }

    public void setNotification_Time(LocalTime notification_Time) {
        this.notification_Time = notification_Time;
    }

    public LocalDate getEvent_Date() {
        return event_Date;
    }

    public void setEvent_Date(LocalDate event_Date) {
        this.event_Date = event_Date;
    }

    public LocalTime getEvent_Time() {
        return event_Time;
    }

    public void setEvent_Time(LocalTime event_Time) {
        this.event_Time = event_Time;
    }

    public String getNotification_description() {
        return notification_description;
    }

    public void setNotification_description(String notification_description) {
        this.notification_description = notification_description;
    }
}
