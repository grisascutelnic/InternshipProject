package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.entity.Feedback;
import com.example.project.entity.Profile;
import com.example.project.service.AnnouncementService;
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

    @GetMapping("/createAnnouncement")
    public String showNewAnnouncement(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "createAnnouncement";
    }

    @PostMapping("/saveAnnouncement")
    public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement,
    @RequestParam("imageFile") MultipartFile imageFile, Authentication auth) {

        Profile profile = profileService.findByEmail(auth.getName());
        announcement.setProfile(profile);

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
    public String viewAnnouncement(@PathVariable("id") Long id, Model model) {
        model.addAttribute("feedback", new Feedback());
        Announcement announcement = announcementService.getAnnouncementById(id);
        if (announcement != null) {
            model.addAttribute("announcement", announcement);
            return "viewAnnouncement";
        } else {
            return "redirect:/viewAnnouncement?error";
        }
    }

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