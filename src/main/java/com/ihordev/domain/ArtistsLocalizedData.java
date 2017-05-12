package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(
    uniqueConstraints = {
            @UniqueConstraint(name = "UNQ_NAME_DESCRIPTION", columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class ArtistsLocalizedData {

    private static final String ARTIST_ID_FK_COLUMN = "ARTIST_ID";
    private static final String LANGUAGE_ID_FK_COLUMN = "LANGUAGE_ID";

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = ARTIST_ID_FK_COLUMN)
        private Long artistId;

        @Column(name = LANGUAGE_ID_FK_COLUMN)
        private Long languageId;

        protected Id() {}

        public Id(Long artistId, Long languageId) {
            this.artistId = artistId;
            this.languageId = languageId;
        }

        @Override
        public boolean equals(@Nullable Object other) {
            if (this == other) return true;
            if (other == null || getClass() != other.getClass()) return false;

            Id otherId = (Id) other;

            if (!this.artistId.equals(otherId.artistId)) return false;
            return languageId.equals(otherId.languageId);
        }

        @Override
        public int hashCode() {
            int result = artistId.hashCode();
            result = 31 * result + languageId.hashCode();
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = LANGUAGE_ID_FK_COLUMN, insertable = false, updatable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = ARTIST_ID_FK_COLUMN, insertable = false, updatable = false)
    private Artist artist;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    public Id getId() {
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
        this.id.artistId = artist.getId();
        this.id.languageId = language.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArtistsLocalizedData)) return false;

        ArtistsLocalizedData that = (ArtistsLocalizedData) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
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
