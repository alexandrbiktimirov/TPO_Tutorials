package com.example.tutorial2.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("original")
public class Original implements IProfile {
    @Override
    public String display(String text) {
        return text;
    }
}
