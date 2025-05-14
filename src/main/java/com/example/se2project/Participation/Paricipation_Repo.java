package com.example.se2project.Participation;
import com.example.se2project.Entities.Participation;
import com.example.se2project.Entities.ParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Paricipation_Repo extends JpaRepository<Participation, ParticipationId> {

    @Query("SELECT p FROM Participation p WHERE p.event.id = :eventId")
    List<Participation> findParticipationsByEventId(@Param("eventId") Integer eventId);

    @Query("SELECT p FROM Participation p WHERE p.user.id = :userId")
    List<Participation> findParticipationsByUserId(@Param("userId") Integer userId);


}
