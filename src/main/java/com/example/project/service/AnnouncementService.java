package com.example.project.service;

import com.example.project.entity.Announcement;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncements();

    void saveAnnouncement(Announcement announcement, MultipartFile imageFile);

    void deleteAnnouncement(Long id);

    void updateAnnouncement(Announcement announcement, MultipartFile imageFile);
}
