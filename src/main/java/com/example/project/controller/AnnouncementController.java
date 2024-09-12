package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/announcements")
@Controller
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/newAnnouncement")
    public String showNewAnnouncement(Model model) {
        model.addAttribute("announcement", new Announcement());
        return "newAnnouncement";
    }

    @PostMapping("/saveAnnouncement")
    public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement,
    @RequestParam("imageFile") MultipartFile imageFile) {
        announcementService.saveAnnouncement(announcement, imageFile);
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
}