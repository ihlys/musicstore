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
public class AlbumsLocalizedData {

    private static final String ALBUM_ID_FK_COLUMN = "ALBUM_ID";
    private static final String LANGUAGE_ID_FK_COLUMN = "LANGUAGE_ID";

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = ALBUM_ID_FK_COLUMN)
        private Long albumId;

        @Column(name = LANGUAGE_ID_FK_COLUMN)
        private Long languageId;

        protected Id() {}

        public Id(Long albumId, Long languageId) {
            this.albumId = albumId;
            this.languageId = languageId;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!albumId.equals(id.albumId)) return false;
            return languageId.equals(id.languageId);
        }

        @Override
        public int hashCode() {
            int result = albumId.hashCode();
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
    @JoinColumn(name = ALBUM_ID_FK_COLUMN, insertable = false, updatable = false)
    private Album album;

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

    public Album getAlbum() {
        return album;
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

    protected AlbumsLocalizedData() {}

    public AlbumsLocalizedData(String name, String description, Album album, Language language) {
        this.name = name;
        this.description = description;

        this.album = album;
        this.language = language;
        this.id.albumId = album.getId();
        this.id.languageId = language.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlbumsLocalizedData)) return false;

        AlbumsLocalizedData that = (AlbumsLocalizedData) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "AlbumsLocalizedData{" +
                "id=" + id +
                ", language=" + language +
                ", album=" + album +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
