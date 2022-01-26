package com.n1ppl3.spring.scanning.main.sub;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubConfiguration {

    @Bean
    public String subConfigurationStringBean() {
        return "some-text";
    }
}
