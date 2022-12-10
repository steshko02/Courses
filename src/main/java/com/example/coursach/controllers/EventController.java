package com.example.coursach.controllers;

import com.example.coursach.dto.EventDto;
import com.example.coursach.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("events")
public class EventController {
    private final EventService eventService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createEvent(@RequestBody EventDto eventDto) {
        eventService.createEvent(eventDto);
    }
}
