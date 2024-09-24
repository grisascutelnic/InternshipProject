package com.example.project.service;

import com.example.project.entity.Event;
import com.example.project.entity.Profile;

import java.util.List;

public interface EventService {
    List<Event> getEventsForProfile(Profile profile);
    Event addEvent(Event event);
    void updateEventStatus(Long eventId, String status);
}
