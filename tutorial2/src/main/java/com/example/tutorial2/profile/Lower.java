package com.example.tutorial2.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("lowerCase")
public class Lower implements IProfile {
    @Override
    public String display(String text) {
        return text.toLowerCase();
    }
}
