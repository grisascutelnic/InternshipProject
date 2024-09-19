package com.example.project.service;


import com.example.project.entity.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    Profile findByEmail(String email);

    Profile findById(Long Id);

    Profile getProfileById(Long id);

    void updateProfile(Long id, Profile editedProfile, MultipartFile imageFile, Authentication auth);

}
