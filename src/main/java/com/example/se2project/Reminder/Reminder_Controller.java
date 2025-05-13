package com.example.se2project.Reminder;
import com.example.se2project.Entities.*;
import com.example.se2project.Event.Event_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Reminder_Controller {

    private final Reminder_Repo reminder_Repo;
    private final Event_Repo event_Repo;


    public Reminder_Controller(Reminder_Repo reminder_Repo, Event_Repo event_Repo) {
        this.reminder_Repo = reminder_Repo;
        this.event_Repo = event_Repo;
    }

    @PostMapping("/Add_Reminder/{eventId}")
    public ResponseEntity<String> Add_Reminder(@PathVariable Integer eventId, @RequestBody ReminderRequest reminderRequest) {
        if(reminderRequest.getReminderDate() == null || reminderRequest.getReminderTime() == null)
            throw new IllegalArgumentException("Reminder Date And Time Cannot Be Null");

        Reminder reminder = new Reminder();
        reminder.setDate(reminderRequest.getReminderDate());
        reminder.setTime(reminderRequest.getReminderTime());
        Event event = event_Repo.findEventById(eventId);
        reminder.setEvent(event);
        reminder_Repo.save(reminder);
        return ResponseEntity.ok("Reminder added");
    }
}
