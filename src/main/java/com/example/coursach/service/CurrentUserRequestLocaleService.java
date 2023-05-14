package com.example.coursach.service;

import com.example.coursach.entity.message.MessageLocale;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class CurrentUserRequestLocaleService {

    public MessageLocale getCurrentLocale() {
        return Optional.of(LocaleContextHolder.getLocale())
                .map(Locale::getLanguage)
                .map(MessageLocale::lookup)
                .orElse(MessageLocale.RU);
    }

}
