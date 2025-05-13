package com.example.se2project.Invitation;
import com.example.se2project.Entities.*;
import com.example.se2project.Event.Event_Repo;
import com.example.se2project.User.User_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class Invitation_Controller {

    private final User_Repo user_repo ;
    private final Event_Repo event_Repo;
    private final Invitation_Repo invitation_Repo;

    public Invitation_Controller(User_Repo userRepo, Event_Repo eventRepo, Invitation_Repo invitationRepo) {
        user_repo = userRepo;
        event_Repo = eventRepo;
        invitation_Repo = invitationRepo;
    }

    @PostMapping("/Add_Invitation/{eventid}")
    public ResponseEntity<String> Add_Invitation(@RequestBody EmailRequest emailRequest, @PathVariable Integer eventid, @CookieValue(value = "userId", required = false) String userId) {
        if (emailRequest.getEmail() == null || emailRequest.getEmail().trim().isEmpty())
            throw new IllegalArgumentException("Email is null or empty!");

        Invitation invitation = new Invitation();
        Optional<User> userLogged = user_repo.findById(Integer.parseInt(userId));
        invitation.setSender(userLogged.get());
        String email = emailRequest.getEmail();
        Optional<User> user = user_repo.findByEmail(email);
        if (user.isPresent()) {
            invitation.setReceiver(user.get());
            Event event = event_Repo.findEventById(eventid);
            invitation.setEvent(event);
            invitation.setStatus("pending");
            invitation_Repo.save(invitation);
            return ResponseEntity.ok("SUCCESS");
        }
        else {
            return ResponseEntity.ok("THIS ACCOUNT IS NOT FOUND");
        }
    }


    @GetMapping("/Get_All_InvitationsSended")
    public ResponseEntity<List<InvitationSender_DTO>> Get_All_InvitationsSended(@CookieValue(value = "userId", required = false) String userId) {
        List<InvitationDTO> invitationsDTO = invitation_Repo.findInvitationsBySenderId(Integer.parseInt(userId));
        List<InvitationSender_DTO> invitations = new ArrayList<>();
        invitationsDTO.forEach(invitationdto -> {
            Optional<User> userOptional = user_repo.findById(invitationdto.getReceiver_id());
            User receiver = userOptional.get();
            Event event = event_Repo.findEventById(invitationdto.getEvent_id());
            InvitationSender_DTO invitation = new InvitationSender_DTO(
                    receiver.getUsername(),
                    receiver.getEmail(),
                    event.getTitle(),
                    invitationdto.getStatus()
            );
            invitations.add(invitation);
        });
        return ResponseEntity.ok(invitations);
    }


    @GetMapping("/Get_All_InvitationsRecevied")
    public ResponseEntity<List<InvitationReceiver_DTO>> Get_All_InvitationsRecevied(@CookieValue(value = "userId", required = false) String userId) {
        List<InvitationDTO> invitationsDTO = invitation_Repo.findInvitationsByReceiverId(Integer.parseInt(userId));
        List<InvitationReceiver_DTO> invitations = new ArrayList<>();
        invitationsDTO.forEach(invitationdto -> {
            if(invitationdto.getStatus().equals("pending")) {
                Optional<User> userOptional = user_repo.findById(invitationdto.getSender_id());
                User Sender = userOptional.get();
                Event event = event_Repo.findEventById(invitationdto.getEvent_id());
                InvitationReceiver_DTO invitation = new InvitationReceiver_DTO(
                        invitationdto.getInvitation_id(),
                        Sender.getUsername(),
                        Sender.getEmail(),
                        event.getTitle(),
                        event.getDate(),
                        event.getTime()
                );
                invitations.add(invitation);
            }
        });
        return ResponseEntity.ok(invitations);
    }





}
