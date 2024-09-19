package com.example.project.service;

import com.example.project.entity.Feedback;
import com.example.project.entity.Profile;
import com.example.project.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class FeedbackServiceImpl implements FeedbackService {

        @Autowired
        private FeedbackRepository feedbackRepository;

        @Autowired
        private ProfileService profileService;

        @Override
        public List<Feedback> getAllFeedbacks() {
            return feedbackRepository.findAll();
        }

        @Override
        public Feedback getFeedbackById(Long id) {
            return feedbackRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        }

        @Override
        public void saveFeedback(Feedback feedback, Authentication authentication) {
            String username = authentication.getName();
            Profile profile = profileService.getProfileByUsername(username);

            // Setează profilul utilizatorului pentru feedback
            if (profile != null) {
                feedback.setProfile(profile);
            }

            feedbackRepository.save(feedback);
        }

//        @Override
//        public void deleteFeedback(Long id) {
//            feedbackRepository.deleteById(id);
//        }
    }


