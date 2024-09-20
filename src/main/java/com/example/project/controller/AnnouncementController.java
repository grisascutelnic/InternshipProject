package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.entity.Feedback;
import com.example.project.entity.Profile;
import com.example.project.service.AnnouncementService;
import com.example.project.service.FeedbackService;
import com.example.project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/announcements")
@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/createAnnouncement")
    public String showNewAnnouncement(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "createAnnouncement";
    }

    @PostMapping("/saveAnnouncement")
    public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement,
                                   @RequestParam("imageFile") MultipartFile imageFile,
                                   @RequestParam(value = "negotiable", required = false) String negotiable,
                                   Authentication auth) {

        Profile profile = profileService.findByEmail(auth.getName());
        announcement.setProfile(profile);

        if (negotiable != null && negotiable.equals("true")) {
            announcement.setPrice(0.0);
        }

        announcementService.saveAnnouncement(announcement, imageFile, auth);
        return "redirect:/index";
    }

    @PostMapping("/updateAnnouncement")
    public String updateTour(@ModelAttribute Announcement announcement,
                             @RequestParam("imageFile") MultipartFile imageFile) {

        announcementService.updateAnnouncement(announcement, imageFile);
        return "redirect:/index";
    }

    @DeleteMapping("/deleteAnnouncement/{id}")
    public String deleteAnnouncement(@RequestParam("id") Long id) {
        announcementService.deleteAnnouncement(id);
        return "redirect:/index";
    }

    @GetMapping("/viewAnnouncement/{id}")
    public String viewAnnouncement(@PathVariable("id") Long id, Model model, Authentication auth) {
        // Obține feedback-urile pentru anunțul specificat
        if (auth != null) {
            Profile profile = profileService.findByEmail(auth.getName());
            model.addAttribute("profile", profile);
        } else {
            model.addAttribute("profile", null); // or handle unauthenticated case differently
        }
        List<Feedback> feedbacks = feedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("feedback", new Feedback());

        // Obține anunțul specificat de ID
        Announcement announcement = announcementService.getAnnouncementById(id);
        if (announcement != null) {
            model.addAttribute("announcement", announcement);

            // Nu este nevoie să adaugi announcementId ca model attribute, deoarece ID-ul este deja parte din URL
            return "viewAnnouncement";
        } else {
            return "redirect:/viewAnnouncement?error";
        }
    }


//    @GetMapping("/viewAnnouncement/{id}")
//    public String viewAnnouncement(@PathVariable("id") Long id, Model model) {
//        Announcement announcement = announcementService.getAnnouncementById(id);
//        if (announcement != null) {
//            List<Feedback> feedbacks = feedbackService.getAllFeedbacks(); // Obține feedback-uri
//            model.addAttribute("announcement", announcement);
//            model.addAttribute("feedbacks", feedbacks); // Adaugă feedback-uri în model
//            return "viewAnnouncement"; // Numele fișierului HTML
//        } else {
//            return "redirect:/viewAnnouncement?error"; // Redirecționează dacă anunțul nu este găsit
//        }
//    }


    @GetMapping("/allAnnouncements")
    public String showAllAnnouncements(Model model) {
        try {
            List<Announcement> announcements = announcementService.getAllAnnouncements();
            model.addAttribute("announcements", announcements);
//            throw new RuntimeException("Simulated exception");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Désolé, la récupération à partir de la base de données a échoué. Veuillez recharger la page et réessayer.");
        }
        return "allAnnouncement";
    }

    @GetMapping("/profile/{id}")
    public String viewProfileAnnouncements(@PathVariable("id") Long id, Model model) {
        Profile profile = profileService.getProfileById(id);
        if (profile != null) {
            model.addAttribute("profile", profile);
           /* model.addAttribute("announcements", profile.getAnnouncements());*/
            return "profile";
        } else {
            return "redirect:/error";
        }
    }

}