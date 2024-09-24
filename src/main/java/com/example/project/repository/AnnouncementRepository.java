package com.example.project.repository;

import com.example.project.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a JOIN a.profile p ORDER BY (CASE WHEN p.numberOfRatings = 0 THEN 0 ELSE p.totalRating / p.numberOfRatings END) DESC")
    List<Announcement> findAllOrderByProfileAverageRatingDesc();

    List<Announcement> findByProfileId(Long profileId);
}