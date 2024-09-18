package com.example.project.repository;

import com.example.project.entity.Announcement;
import com.example.project.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findByEmail(String email);
//    List<Announcement> findByProfileId(Long profileId);
}
