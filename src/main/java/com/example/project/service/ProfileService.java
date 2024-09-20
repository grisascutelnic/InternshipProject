package com.example.project.service;


import com.example.project.entity.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    Profile getProfileByUsername(String username);

    Profile findByEmail(String email);

    Profile findById(Long Id);

    Profile getProfileById(Long id);

    void updateProfile(Long id, Profile editedProfile, MultipartFile imageFile, Authentication auth);

    Profile findProfileById(Long profileId);

    void save(Profile profile);
}
