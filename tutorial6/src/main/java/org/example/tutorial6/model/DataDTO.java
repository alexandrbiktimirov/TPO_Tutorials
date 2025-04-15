package org.example.tutorial6.model;

import java.util.List;

public class DataDTO {
    private final Integer entries;
    private final String language;
    private final List<String> fields;

    public DataDTO(Integer entries, String language, List<String> fields) {
        this.entries = entries;
        this.language = language;
        this.fields = fields;
    }

    public Integer getEntries() {
        return entries;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getFields() {
        return fields;
    }
}
