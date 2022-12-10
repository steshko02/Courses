package com.example.coursach.service;

import com.example.coursach.converters.EventConverter;
import com.example.coursach.dto.EventDto;
import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventConverter eventConverter;
    private final EventRepository eventRepository;

    public void createEvent(EventDto eventDto) {
        eventRepository.save(eventConverter.toEntity(eventDto));
    }

    public EventDto getById(Long id) {
        return eventConverter.toDto(eventRepository.findById(id).get());
    }


}
