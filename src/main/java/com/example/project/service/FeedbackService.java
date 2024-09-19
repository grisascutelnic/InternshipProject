package com.example.project.service;

import com.example.project.entity.Feedback;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(Long id);
    void saveFeedback(Feedback feedback, Authentication authentication);
    //    void deleteFeedback(Long id);
}


