package com.example.se2project.Event;
import com.example.se2project.Entities.*;
import com.example.se2project.EventStatus.EventStatus_Repo;
import com.example.se2project.Invitation.Invitation_Repo;
import com.example.se2project.Participation.Paricipation_Repo;
import com.example.se2project.Reminder.Reminder_Repo;
import com.example.se2project.User.User_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


@RestController
@RequestMapping("/api")
public class Event_Controller {
    final private User_Repo user_repo ;
    final private EventStatus_Repo eventstatus_repo ;
    private final Event_Repo event_Repo;
    private final Reminder_Repo reminder_Repo;
    private final Invitation_Repo invitation_Repo;
    private final Paricipation_Repo paricipation_Repo;

    public Event_Controller(User_Repo user_repo, EventStatus_Repo eventstatus_repo, Event_Repo event_Repo, Reminder_Repo reminder_Repo, Invitation_Repo invitation_Repo, Paricipation_Repo paricipation_Repo) {
        this.user_repo = user_repo;
        this.eventstatus_repo = eventstatus_repo;
        this.event_Repo = event_Repo;
        this.reminder_Repo = reminder_Repo;
        this.invitation_Repo = invitation_Repo;
        this.paricipation_Repo = paricipation_Repo;
    }

    @PostMapping("/Add_Event")
    public ResponseEntity<String> Add_Event(@RequestBody Event eventsended, @CookieValue(value = "userId", required = false) String userId) {
        Event event = new Event();
        event.setTitle(eventsended.getTitle());
        event.setDescription(eventsended.getDescription());
        event.setDate(eventsended.getDate());
        event.setTime(eventsended.getTime());
        Optional<User> user = user_repo.findById(Integer.parseInt(userId));
        event.setUser(user.get());

        LocalDate eventDate = event.getDate();
        LocalTime eventTime = event.getTime();
        LocalDateTime eventDateTime = LocalDateTime.of(eventDate, eventTime);
        LocalDateTime now = LocalDateTime.now();

        if (eventDateTime.isBefore(now)) {
            Eventstatus eventstatus = eventstatus_repo.findById(1);
            event.setEventstatus(eventstatus);
        }
        else {
            Eventstatus eventstatus = eventstatus_repo.findById(2);
            event.setEventstatus(eventstatus);
        }
        event_Repo.save(event);
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/GetAllEvents")
    public ResponseEntity<List<EventDTO>> getAllEvents(@CookieValue(value = "userId", required = false) String userId) {
        // Events created by the user
        List<EventDTO> eventsDTO1 = event_Repo.findEventByUserId(Integer.parseInt(userId));
        for (EventDTO e : eventsDTO1) {
            e.setEventSource("Created");
        }

        // Events the user is participating in
        List<Participation> participations = paricipation_Repo.findParticipationsByUserId(Integer.parseInt(userId));
        List<EventDTO> eventsDTO2 = new ArrayList<>();
        for (Participation participation : participations) {
            Event event = event_Repo.findEventById(participation.getEvent().getId());
            Eventstatus eventstatus = event.getEventstatus();

            // Avoid duplication if user is already the creator
            boolean alreadyAdded = eventsDTO1.stream().anyMatch(e -> e.getEvent_id() == event.getId());
            if (alreadyAdded) continue;

            EventDTO eventDTO = new EventDTO(
                    event.getId(),
                    event.getDate(),
                    event.getDescription(),
                    eventstatus.getId(),
                    event.getTime(),
                    event.getTitle()
            );
            eventDTO.setEventSource("Participated");

            eventsDTO2.add(eventDTO);
        }

        // Combine all events
        List<EventDTO> allEvents = new ArrayList<>();
        allEvents.addAll(eventsDTO1);
        allEvents.addAll(eventsDTO2);

        return ResponseEntity.ok(allEvents);
    }






    @PutMapping("/Edit_Event/{id}")
    public ResponseEntity<String> Edit_Event(
            @PathVariable Integer id,
            @RequestBody Event updatedEvent) {

        Event event = event_Repo.findById(id).get();
        event.setTitle(updatedEvent.getTitle());
        event.setDescription(updatedEvent.getDescription());
        event.setDate(updatedEvent.getDate());
        event.setTime(updatedEvent.getTime());
        event_Repo.save(event);
        return ResponseEntity.ok("SUCCESS");
    }


    @DeleteMapping("/Delete_Event/{id}")
    public ResponseEntity<String> Delete_Event(@PathVariable Integer id){

        Event event = event_Repo.findById(id).get();

        Reminder reminder = reminder_Repo.findByEventId(id);
        if (reminder != null) {
            reminder_Repo.delete(reminder);
        }

        List<Invitation> invitations = invitation_Repo.findInvitationsByEventId(id);
        if (invitations.size() > 0) {
            invitation_Repo.deleteAll(invitations);
        }

        List<Participation> participations = paricipation_Repo.findParticipationsByEventId(id);
        if (participations.size() > 0) {
            paricipation_Repo.deleteAll(participations);
        }

        event_Repo.delete(event);
        return ResponseEntity.ok("SUCCESS");
    }




}