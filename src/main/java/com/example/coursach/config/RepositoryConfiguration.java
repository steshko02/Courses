package com.example.coursach.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.example.coursach.repository")
public class RepositoryConfiguration {
}
