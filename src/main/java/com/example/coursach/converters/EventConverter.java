package com.example.coursach.converters;

import com.example.coursach.dto.EventDto;
import com.example.coursach.entity.Event;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
