package com.example.coursach.config;

import com.example.coursach.config.properties.ApplicationUrlProperties;
import com.example.coursach.config.properties.JwtProperties;
import com.example.coursach.config.properties.MailProperties;
import com.example.coursach.config.properties.NotificationProperties;
import com.example.coursach.config.properties.RegexProperties;
import com.example.coursach.config.properties.UserProperties;
import com.example.coursach.config.properties.minio.MinioDataStorageProperties;
import com.example.coursach.config.properties.minio.MinioServerConfigurationProperties;
import com.example.coursach.service.converter.resolvers.urlresolver.UrlResolver;
import com.example.coursach.service.validator.FieldValidator;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@EnableConfigurationProperties({
        JwtProperties.class,
        RegexProperties.class,
        NotificationProperties.class,
        MailProperties.class,
        MinioDataStorageProperties.class,
        UserProperties.class,
        ApplicationUrlProperties.class,
        MinioServerConfigurationProperties.class

})
public class CommonConfiguration {

    @Bean
    public Map<ValidationType, FieldValidator> validators(List<FieldValidator> validatorList) {
        return validatorList.stream().collect(Collectors.toMap(FieldValidator::getType, Function.identity()));
    }

    @Bean
    public Clock systemDefaultClock() {
        return Clock.systemUTC();
    }

    @Bean
    public DateTimeFormatter zonedDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DateTimeFormat.ZONE_DATE_TIME_PATTEN);
    }

    @Bean
    public Map<String, UrlResolver> urlResolverMap(List<UrlResolver> urlResolverList) {
        Map<String, UrlResolver> map = new HashMap<>();

        urlResolverList.forEach(
                resolver ->
                        resolver.getNotificationType().forEach(
                                type -> map.put(type, resolver)
                        )
        );

        return map;
    }
}
