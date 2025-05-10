package com.example.se2project.Event;
import com.example.se2project.Entities.Event;
import com.example.se2project.Entities.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface Event_Repo extends JpaRepository<Event, Integer> {

    @Query("SELECT new com.example.se2project.Entities.EventDTO(" +
            "e.id, e.date, e.description, e.eventstatus.id, e.time, e.title) " +
            "FROM Event e WHERE e.user.id = :userId")
    List<EventDTO> findEventByUserId(@Param("userId") Integer userId);

    Event findEventById(int id);
}
