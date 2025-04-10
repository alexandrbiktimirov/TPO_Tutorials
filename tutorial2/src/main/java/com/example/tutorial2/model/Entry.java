package com.example.tutorial2.model;

public class Entry {

    private String polishVersion;
    private String englishVersion;
    private String germanVersion;

    public Entry(String polishVersion, String englishVersion, String germanVersion) {
        this.polishVersion = polishVersion;
        this.englishVersion = englishVersion;
        this.germanVersion = germanVersion;
    }

    public String getPolishVersion() {
        return polishVersion;
    }

    public void setPolishVersion(String polishVersion) {
        this.polishVersion = polishVersion;
    }

    public String getEnglishVersion() {
        return englishVersion;
    }

    public void setEnglishVersion(String englishVersion) {
        this.englishVersion = englishVersion;
    }

    public String getGermanVersion() {
        return germanVersion;
    }

    public void setGermanVersion(String germanVersion) {
        this.germanVersion = germanVersion;
    }

    @Override
    public String toString() {
        return  "Polish version='" + polishVersion + '\'' +
                ", English version='" + englishVersion + '\'' +
                ", German version='" + germanVersion + '\'';
    }
}
