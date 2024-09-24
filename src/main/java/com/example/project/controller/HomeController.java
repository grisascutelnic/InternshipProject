package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.entity.Feedback;
import com.example.project.service.AnnouncementService;
import com.example.project.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/index")
    public String showIndex(Model model) {
        try {
            List<Announcement> announcements = announcementService.getAnnouncementsSortedByRating();
            model.addAttribute("announcements", announcements);
//            throw new RuntimeException("Simulated exception");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Désolé, la récupération à partir de la base de données a échoué. Veuillez recharger la page et réessayer.");
        }
        return "index";
    }
}
