package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.BookingStatus;
import jakarta.persistence.AttributeConverter;

public class BookingStatusConverter implements AttributeConverter<BookingStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(BookingStatus bookingStatus) {
        return bookingStatus.getVal();
    }

    @Override
    public BookingStatus convertToEntityAttribute(Integer integer) {
        return BookingStatus.lookup(integer);
    }
}
