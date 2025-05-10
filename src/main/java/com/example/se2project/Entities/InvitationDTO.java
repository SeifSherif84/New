package com.example.se2project.Entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class InvitationDTO {
    private Integer invitation_id;
    private String status;
    private Integer sender_id;
    private Integer receiver_id;
    private Integer event_id;

    public InvitationDTO(Integer invitation_id, String status, Integer sender_id, Integer receiver_id, Integer event_id) {
        this.invitation_id = invitation_id;
        this.status = status;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.event_id = event_id;
    }

    public Integer getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(Integer invitation_id) {
        this.invitation_id = invitation_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSender_id() {
        return sender_id;
    }

    public void setSender_id(Integer sender_id) {
        this.sender_id = sender_id;
    }

    public Integer getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(Integer receiver_id) {
        this.receiver_id = receiver_id;
    }

    public Integer getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Integer event_id) {
        this.event_id = event_id;
    }
}
