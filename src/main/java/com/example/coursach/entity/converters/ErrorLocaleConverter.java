package com.example.coursach.entity.converters;

import com.example.coursach.entity.message.MessageLocale;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ErrorLocaleConverter implements AttributeConverter<MessageLocale, String> {

    @Override
    public String convertToDatabaseColumn(MessageLocale messageLocale) {
        return messageLocale.getLanguage();
    }

    @Override
    public MessageLocale convertToEntityAttribute(String language) {
        return MessageLocale.lookup(language);
    }

}
