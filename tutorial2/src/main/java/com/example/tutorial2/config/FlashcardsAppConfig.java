package com.example.tutorial2.config;

import com.example.tutorial2.model.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FlashcardsAppConfig {
    @Bean
    public List<Entry> entryList() {
        return new ArrayList<>();
    }
}
