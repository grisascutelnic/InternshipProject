package com.example.project.repository;

import com.example.project.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByAnnouncementId(Long announcementId);
}
