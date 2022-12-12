//package com.example.coursach.controllers;
//
//import com.example.coursach.dto.EventDto;
//import com.example.coursach.dto.ProfileUserDto;
//import com.example.coursach.service.EventService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("events")
//public class EventController {
//    private final EventService eventService;
//
//    @PostMapping
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void createEvent(@RequestBody EventDto eventDto) {
//        eventService.createEvent(eventDto);
//    }
//
//    @GetMapping("/{id}")
//    @ResponseBody
//    public EventDto get(@PathVariable("id") Long id) {
//        return eventService.getById(id);
//    }
//}
