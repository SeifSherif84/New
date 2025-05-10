package com.example.se2project.EventStatus;
import com.example.se2project.Entities.Eventstatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatus_Repo extends JpaRepository<Eventstatus,Integer> {
    Eventstatus findById(int id);
}
