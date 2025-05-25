package org.example.tutorial11.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import org.example.tutorial11.constraint.ValidPassword;
import org.hibernate.validator.constraints.URL;

import java.util.Optional;

public class CreateLinkDTO {

    @NotNull
    @Size(min = 5, max = 20)
    public String name;

    @NotNull
    @URL(message = "Invalid URL provided")
    @Pattern(regexp = "^https://.+", message = "Only HTTPS URLs are allowed")
    public String targetUrl;

    @ValidPassword
    public String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
