package com.example.se2project.Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventDTO {

    private Integer event_id;
    private LocalDate date;
    private String description;
    private Integer eventstatus_id;
    private LocalTime time;
    private String title;
    private String eventSource; // "Created" or "Participated"

    public EventDTO(Integer event_id, LocalDate date, String description, Integer eventstatus_id, LocalTime time, String title) {
        this.event_id = event_id;
        this.date = date;
        this.description = description;
        this.eventstatus_id = eventstatus_id;
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

    public Integer getEventstatus_id() {
        return eventstatus_id;
    }

    public void setEventstatus_id(Integer eventstatus_id) {
        this.eventstatus_id = eventstatus_id;
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
