package com.example.project.service;

import com.example.project.entity.Announcement;
import com.example.project.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void saveAnnouncement(Announcement announcement, MultipartFile imageFile) {

        try {
            if (!imageFile.isEmpty()) {
                announcement.setImage(imageFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image", e); // Add more specific error handling if needed
        }

        announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}
