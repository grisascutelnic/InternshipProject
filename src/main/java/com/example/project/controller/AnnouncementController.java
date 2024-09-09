package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement) {
        announcementService.saveAnnouncement(announcement);
        return "redirect:/index";
    }
}