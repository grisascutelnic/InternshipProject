package com.example.project.service;

import com.example.project.entity.Announcement;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> getAllAnnouncements();

    void saveAnnouncement(Announcement announcement, MultipartFile imageFile, Authentication auth);

    void deleteAnnouncement(Long id);

    void updateAnnouncement(Announcement announcement, MultipartFile imageFile);

    Announcement getAnnouncementById(Long id);

    List<Announcement> findByProfileId(Long profileId);


}
