package com.example.project.repository;

import com.example.project.entity.Event;
import com.example.project.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByProfile(Profile profile);
    List<Event> findByProfileAndStatus(Profile profile, String status);
}
