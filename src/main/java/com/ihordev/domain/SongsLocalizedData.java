package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;



@Entity
public class SongsLocalizedData {

    private static final String SONGS_ID_FK_COLUMN = "SONGS_ID";
    private static final String LANGUAGE_ID_FK_COLUMN = "LANGUAGE_ID";

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = SONGS_ID_FK_COLUMN)
        private Long songId;

        @Column(name = LANGUAGE_ID_FK_COLUMN)
        private Long languageId;

        protected Id() {}

        public Id(Long songId, Long languageId) {
            this.songId = songId;
            this.languageId = languageId;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!songId.equals(id.songId)) return false;
            return languageId.equals(id.languageId);
        }

        @Override
        public int hashCode() {
            int result = songId.hashCode();
            result = 31 * result + languageId.hashCode();
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = SONGS_ID_FK_COLUMN, insertable = false, updatable = false)
    private Song song;

    @ManyToOne
    @JoinColumn(name = LANGUAGE_ID_FK_COLUMN, insertable = false, updatable = false)
    private Language language;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    public Id getId() {
        return id;
    }

    public Song getSong() {
        return song;
    }

    public Language getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected SongsLocalizedData() {}

    public SongsLocalizedData(String name, Song song, Language language) {
        this.name = name;

        this.song = song;
        this.language = language;
        this.id.songId = song.getId();
        this.id.languageId = language.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongsLocalizedData)) return false;

        SongsLocalizedData that = (SongsLocalizedData) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "SongsLocalizedData{" +
                "id=" + id +
                ", song=" + song +
                ", language=" + language +
                ", name='" + name + '\'' +
                '}';
    }
}
