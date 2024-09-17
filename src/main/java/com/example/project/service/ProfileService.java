package com.example.project.service;


import com.example.project.entity.Profile;

public interface ProfileService {
    Profile findByEmail(String email);
}
