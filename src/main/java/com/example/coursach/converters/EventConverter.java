package com.example.coursach.converters;

import com.example.coursach.dto.EventDto;
import com.example.coursach.entity.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@AllArgsConstructor
public class EventConverter {

    public Event toEntity(EventDto eventDto) {

        return Event.builder()
                .date(eventDto.getDate().toLocalDateTime())
                .message(eventDto.getMessage())
                .subject(eventDto.getSubject())
                .build();
    }

    public EventDto toDto(Event event) {
        return EventDto.builder()
                .date(event.getDate().atZone(ZoneId.systemDefault()))
                .subject(event.getSubject())
                .message(event.getMessage())
                .build();
    }
}
