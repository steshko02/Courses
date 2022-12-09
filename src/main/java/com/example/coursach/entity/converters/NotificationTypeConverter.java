package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.NotificationType;
import jakarta.persistence.AttributeConverter;

public class NotificationTypeConverter  implements AttributeConverter<NotificationType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationType notificationType) {
        return notificationType.getVal();
    }

    @Override
    public NotificationType convertToEntityAttribute(Integer integer) {
        return NotificationType.lookup(integer);
    }
}
