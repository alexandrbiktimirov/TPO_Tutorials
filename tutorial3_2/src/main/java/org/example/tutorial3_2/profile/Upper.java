package org.example.tutorial3_2.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("upperCase")
public class Upper implements IProfile {

    @Override
    public String display(String text) {
        return text.toUpperCase();
    }

}