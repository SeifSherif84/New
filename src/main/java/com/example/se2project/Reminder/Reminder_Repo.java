package com.example.se2project.Reminder;
import com.example.se2project.Entities.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Reminder_Repo extends JpaRepository<Reminder, Integer> {
    boolean existsByEventId(Integer eventId);
    Reminder findByEventId(Integer eventId);
}
