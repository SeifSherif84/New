package com.example.se2project.Participation;
import com.example.se2project.Entities.*;
import com.example.se2project.Invitation.Invitation_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Participation_Controller  {
    private final Paricipation_Repo paricipationRepo;
    private final Invitation_Repo invitation_Repo;

    public Participation_Controller(Paricipation_Repo paricipationRepo, Invitation_Repo invitation_Repo) {
        this.paricipationRepo = paricipationRepo;
        this.invitation_Repo = invitation_Repo;
    }

    @PostMapping("/Accept_Invitation/{invitationId}")
    public ResponseEntity<String> Accept_Invitation(@PathVariable Integer invitationId) {
        Participation SenderParticipation = new Participation();
        Participation ReceiverParticipation = new Participation();
        Invitation invitation = invitation_Repo.findById(invitationId).get();
        invitation.setStatus("accepted");
        invitation_Repo.save(invitation);
        User Sender = invitation.getSender();
        User Receiver = invitation.getReceiver();
        Event event = invitation.getEvent();

        ParticipationId id1 = new ParticipationId();
        ParticipationId id2 = new ParticipationId();
        id1.setUserId(Sender.getId());
        id1.setEventId(event.getId());
        id2.setUserId(Receiver.getId());
        id2.setEventId(event.getId());

        SenderParticipation.setId(id1);
        SenderParticipation.setUser(Sender);
        SenderParticipation.setEvent(event);
        ReceiverParticipation.setId(id2);
        ReceiverParticipation.setUser(Receiver);
        ReceiverParticipation.setEvent(event);
        paricipationRepo.save(SenderParticipation);
        paricipationRepo.save(ReceiverParticipation);
        return ResponseEntity.ok("Accepted");
    }


    @PostMapping("/Decline_Invitation/{invitationId}")
    public ResponseEntity<String> Decline_Invitation(@PathVariable Integer invitationId) {
        Invitation invitation = invitation_Repo.findById(invitationId).get();
        invitation.setStatus("rejected");
        invitation_Repo.save(invitation);
        return ResponseEntity.ok("Rejected");
    }

}
