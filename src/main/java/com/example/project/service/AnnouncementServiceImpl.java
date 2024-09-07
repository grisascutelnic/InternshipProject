package com.example.project.service;

import com.example.project.entity.Announcement;
import com.example.project.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public void saveAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }
}
