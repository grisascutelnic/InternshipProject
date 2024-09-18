package com.example.project.service;

import com.example.project.entity.Announcement;
import com.example.project.exceptions.InternalServerErrorException;
import com.example.project.exceptions.NotFoundException;
import com.example.project.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Date;
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
    public void saveAnnouncement(Announcement announcement, MultipartFile imageFile, Authentication auth) {

        try {
            if (!imageFile.isEmpty()) {
                announcement.setImage(imageFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload image", e); // Add more specific error handling if needed
        }
        announcement.setDate(new Date());
        announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public void updateAnnouncement(Announcement updatedAnnouncement, MultipartFile imageFile) {
        try {
            Announcement announcement = announcementRepository.findById(updatedAnnouncement.getId())
                    .orElseThrow(() -> new NotFoundException("Invalid tour id: " + updatedAnnouncement.getId()));

            announcement.setTitle(updatedAnnouncement.getTitle());
            announcement.setPrice(updatedAnnouncement.getPrice());
            announcement.setDescription(updatedAnnouncement.getDescription());
            announcement.setNumber(announcement.getNumber());

            if (!imageFile.isEmpty()) {
                try {
                    byte[] imageBytes = imageFile.getBytes();
                    announcement.setImage(imageBytes);
                } catch (IOException e) {
                    throw new InternalServerErrorException("Failed to save image", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            announcementRepository.save(announcement);
        } catch (Exception e) {
            throw new InternalServerErrorException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Announcement not found with id: " + id));
    }

    @Override
    public List<Announcement> findByProfileId(Long profileId) {
        return announcementRepository.findByProfileId(profileId);
    }
}
