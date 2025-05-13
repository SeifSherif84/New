package com.example.se2project.Notification;
import com.example.se2project.Entities.*;
import com.example.se2project.Event.Event_Repo;
import com.example.se2project.Reminder.Reminder_Repo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class Notification_Controller {

    private final Event_Repo event_Repo;
    private final Reminder_Repo reminder_Repo;

    public Notification_Controller(Event_Repo event_Repo, Reminder_Repo reminder_Repo) {
        this.event_Repo = event_Repo;
        this.reminder_Repo = reminder_Repo;
    }

    @GetMapping("/Get_All_Notification")
    public ResponseEntity<List<Notification>> Get_All_Notification(@CookieValue(value = "userId", required = false) String userId){
        List<EventDTO> eventList = event_Repo.findEventsDtoByUserId(Integer.parseInt(userId));
        List<EventDTO> reminderedEventsList = new ArrayList<>();
        eventList.forEach(event -> {
            if(reminder_Repo.existsByEventId(event.getEvent_id())){
                reminderedEventsList.add(event);
            }
        });

        LocalDateTime now = LocalDateTime.now();

        List<Notification> notificationList = new ArrayList<>();
        reminderedEventsList.forEach(reminderdEvent -> {
            Reminder reminder = reminder_Repo.findByEventId(reminderdEvent.getEvent_id());
            LocalDateTime reminderDateTime = LocalDateTime.of(reminder.getDate(), reminder.getTime());
                if(!now.isBefore(reminderDateTime)){
                    Notification notification = new Notification(
                            reminderdEvent.getTitle(),
                            reminder.getDate(),
                            reminder.getTime(),
                            reminderdEvent.getDate(),
                            reminderdEvent.getTime(),
                            "Tick tock... Don't let it slip your mind!"
                    );
                    notificationList.add(notification);
                }
        });
        return ResponseEntity.ok(notificationList);
    }

}
