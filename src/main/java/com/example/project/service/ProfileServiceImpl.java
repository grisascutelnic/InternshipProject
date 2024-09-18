package com.example.project.service;

import com.example.project.entity.Profile;
import com.example.project.exceptions.InternalServerErrorException;
import com.example.project.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).orElse(null);
    }

    @Override
    public void updateProfile(Profile editedProfile, MultipartFile imageFile, Authentication auth) {
        try {
            Profile profile = profileRepository.findByEmail(auth.getName());
            profile.setFirstName(editedProfile.getFirstName());
            profile.setLastName(editedProfile.getLastName());
//            profile.setEmail(editedProfile.getEmail());
            profile.setPhone(editedProfile.getPhone());
            profile.setAddress(editedProfile.getAddress());
            profile.setLocation(editedProfile.getLocation());
            profile.setDescription(editedProfile.getDescription());

            if (!imageFile.isEmpty()) {
                try {
                    byte[] imageBytes = imageFile.getBytes();
                    profile.setImage(imageBytes);
                } catch (IOException e) {
                    throw new InternalServerErrorException("Failed to save image", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }

            profileRepository.save(profile);
        } catch (Exception e) {
            throw new InternalServerErrorException("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
