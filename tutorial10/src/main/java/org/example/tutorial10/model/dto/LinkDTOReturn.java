package org.example.tutorial10.model.dto;

public class LinkDTOReturn {
    public String id;
    public String name;
    public String targetUrl;
    public String redirectUrl;
    public int visits;

    public LinkDTOReturn(String id, String name, String targetUrl, String redirectUrl, int visits) {
        this.id = id;
        this.name = name;
        this.targetUrl = targetUrl;
        this.redirectUrl = redirectUrl;
        this.visits = visits;
    }
}
