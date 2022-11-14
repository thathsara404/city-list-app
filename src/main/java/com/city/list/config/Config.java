package com.city.list.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Default configurations.
 * */
@AllArgsConstructor
@Configuration
public class Config {

    private Environment environment;

    final Logger logger = LoggerFactory.getLogger(Config.class);

    @Bean
    public String getProfileInfo(@Value("${spring.application.name}") final String appName) {
        logger.info("App {} is running with profile {} .", appName, environment.getActiveProfiles());
        return environment.getActiveProfiles().toString();
    }

}
