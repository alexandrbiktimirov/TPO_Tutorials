package org.example.tutorial3.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="Entry")
public class Entry {

    @Id
    private Long id;

    @Column(name = "polish_version")
    private String polishVersion;

    @Column(name = "english_version")
    private String englishVersion;

    @Column(name = "german_version")
    private String germanVersion;

    public Entry() {
    }

    public Entry(String polishVersion, String englishVersion, String germanVersion) {
        this.polishVersion = polishVersion;
        this.englishVersion = englishVersion;
        this.germanVersion = germanVersion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(polishVersion, entry.polishVersion) && Objects.equals(englishVersion, entry.englishVersion) && Objects.equals(germanVersion, entry.germanVersion);
    }
}
