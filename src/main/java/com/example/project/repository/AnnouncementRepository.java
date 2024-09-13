package com.example.project.repository;

import com.example.project.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("SELECT a FROM Announcement a WHERE a.title LIKE %:keyword%")
    List<Announcement> searchByKeyword(@Param("keyword") String keyword);
}
