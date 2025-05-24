package org.example.tutorial10.model.dto;

import java.util.Optional;

public class CreateLinkDTO {
    public String name;
    public String password;
    public String targetUrl;

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
