package com.example.project.service;

import com.example.project.entity.Announcement;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncements();

    void saveAnnouncement(Announcement announcement);
}
