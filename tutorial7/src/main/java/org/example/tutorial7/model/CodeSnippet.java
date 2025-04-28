package org.example.tutorial7.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CodeSnippet implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String originalCode;
    private final String formattedCode;
    private final LocalDateTime expiry;

    public CodeSnippet(String originalCode, String formattedCode, Integer duration) {
        this.originalCode = originalCode;
        this.formattedCode = formattedCode;
        this.expiry = LocalDateTime.now().plusSeconds(duration);
    }

    public String getOriginalCode() {
        return originalCode;
    }

    public String getFormattedCode() {
        return formattedCode;
    }

    public LocalDateTime getExpiry() {
        return expiry;
    }
}
