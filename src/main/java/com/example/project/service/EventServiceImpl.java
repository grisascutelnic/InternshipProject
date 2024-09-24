package com.example.project.service;

import com.example.project.entity.Event;
import com.example.project.entity.Profile;
import com.example.project.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getEventsForProfile(Profile profile) {
        return eventRepository.findByProfile(profile);
    }

    @Override
    public Event addEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void updateEventStatus(Long eventId, String status) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setStatus(status);
        eventRepository.save(event);
    }
}
