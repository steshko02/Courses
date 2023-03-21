package com.example.coursach.service.model.mail.utils;

import eu.senla.git.coowning.config.properties.MailProperties;
import eu.senla.git.coowning.entity.message.MessageLocale;
import eu.senla.git.coowning.service.model.mail.enums.MailScope;
import eu.senla.git.coowning.storage.pattern.Patterns;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateUtils {

    private final MailProperties mailProperties;

    @SneakyThrows
    public String getLetterTemplate(MailScope mailScope, MessageLocale locale) {
        final String finalFilename = String.format(Patterns.SERVER_FILE_STORAGE, mailProperties.getTemplatesLocation(),
                mailScope.getTemplateLocation().replace(mailProperties.getLocalePostfix(), locale.getLanguage()));

        StringBuilder resultStringBuilder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new ClassPathResource(finalFilename).getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            resultStringBuilder.append(line).append("\n");
        }

        return resultStringBuilder.toString();
    }

    public String constructLetter(String template, Map<String, String> replacement) {
        String constructedTemplate = template;

        for (Map.Entry<String, String> entry : replacement.entrySet()) {
            constructedTemplate = constructedTemplate.replace(entry.getKey(), entry.getValue());
        }

        return constructedTemplate;
    }

}
