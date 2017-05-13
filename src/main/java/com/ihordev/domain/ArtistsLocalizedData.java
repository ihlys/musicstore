package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_LANGUAGE_ARTIST", columnNames = {"LANGUAGE", "ARTIST"}),
        @UniqueConstraint(name = "UNQ_NAME_DESCRIPTION", columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class ArtistsLocalizedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotNull
    @ManyToOne(optional = false)
    private Artist artist;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected ArtistsLocalizedData() {}

    public ArtistsLocalizedData(String name, String description, Artist artist, Language language) {
        this.name = name;
        this.description = description;
        this.artist = artist;
        this.language = language;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistsLocalizedData)) return false;

        ArtistsLocalizedData that = (ArtistsLocalizedData) o;

        if (!getName().equals(that.getName())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ArtistsLocalizedData{" +
                "language=" + language.getId() +
                ", artist=" + artist.getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
