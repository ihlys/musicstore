package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_ARTIST_L10N_LANG_ARTIST",
                columnNames = {"LANGUAGE_ID", "ARTIST_ID"}),
        @UniqueConstraint(name = "UNQ_ARTIST_L10N_NAME_DESCRIP",
                columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class ArtistL10n {

    @Id
    @GeneratedValue(generator = "ARTIST_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ARTIST_L10N_SEQ_GEN", sequenceName = "ARTIST_L10N_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Artist artist;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1)
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

    protected ArtistL10n() {}

    public ArtistL10n(String name, String description, Artist artist, Language language) {
        this.name = name;
        this.description = description;
        this.artist = artist;
        this.language = language;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistL10n)) return false;

        ArtistL10n that = (ArtistL10n) o;

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
        return "ArtistL10n{" +
                "\n language: " + language.getId() +
                ";\n artist: " + artist.getId() +
                ";\n name: " + name +
                ";\n description: " + description +
                "}";
    }
}
