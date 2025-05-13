package com.example.se2project.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {

    private Integer event_id;
    private LocalDate date;
    private String description;
    private String eventstatus;
    private LocalTime time;
    private String title;
    private String eventSource;

    public EventDTO(Integer event_id, LocalDate date, String description, String eventstatus, LocalTime time, String title) {
        this.event_id = event_id;
        this.date = date;
        this.description = description;
        this.eventstatus = eventstatus;
        this.time = time;
        this.title = title;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventstatus() { return eventstatus; }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventSource() {
        return eventSource;
    }

    public void setEventSource(String eventSource) {
        this.eventSource = eventSource;
    }


}
