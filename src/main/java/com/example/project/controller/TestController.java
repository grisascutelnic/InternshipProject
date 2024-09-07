package com.example.project.controller;

import com.example.project.entity.Announcement;
import com.example.project.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private AnnouncementService announcementService;

    @GetMapping("/announcements")
    public List<Announcement> getAnnouncements() {
        return announcementService.getAllAnnouncements();
    }
}
