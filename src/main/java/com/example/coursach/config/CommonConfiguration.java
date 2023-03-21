package com.example.coursach.config;

import com.example.coursach.config.properties.JwtProperties;
import com.example.coursach.config.properties.RegexProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties({
        JwtProperties.class,
        RegexProperties.class
})
public class CommonConfiguration {

    @Bean
    public Clock systemDefaultClock() {
        return Clock.systemUTC();
    }
}
