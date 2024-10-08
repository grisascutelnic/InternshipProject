package com.example.project.service;

import com.example.project.entity.Feedback;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(Long id);
    void saveFeedback(Feedback feedback);
    //    void deleteFeedback(Long id);

    List<Feedback> getFeedbacksByAnnouncementId(Long announcementId);
}


