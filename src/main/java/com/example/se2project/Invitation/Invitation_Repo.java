package com.example.se2project.Invitation;
import com.example.se2project.Entities.Invitation;
import com.example.se2project.Entities.InvitationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface Invitation_Repo extends JpaRepository<Invitation,Integer> {
    @Query("SELECT new com.example.se2project.Entities.InvitationDTO(" +
            "e.id, e.status, e.sender.id, e.receiver.id, e.event.id) " +
            "FROM Invitation e WHERE e.sender.id = :userId")
    List<InvitationDTO> findInvitationsBySenderId(@Param("userId") Integer userId);

    @Query("SELECT new com.example.se2project.Entities.InvitationDTO(" +
            "e.id, e.status, e.sender.id, e.receiver.id, e.event.id) " +
            "FROM Invitation e WHERE e.receiver.id = :userId")
    List<InvitationDTO> findInvitationsByReceiverId(@Param("userId") Integer userId);

    Optional<Invitation> findById(Integer id);

    @Query("SELECT e FROM Invitation e WHERE e.event.id = :eventId")
    List<Invitation> findInvitationsByEventId(@Param("eventId") Integer eventId);

}

