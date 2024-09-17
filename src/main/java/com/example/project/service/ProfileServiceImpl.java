package com.example.project.service;

import com.example.project.entity.Profile;
import com.example.project.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }
}
