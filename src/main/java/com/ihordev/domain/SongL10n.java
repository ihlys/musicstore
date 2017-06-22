package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_SONG_L10N_LANG_SONG",
                columnNames = {"LANGUAGE_ID", "SONG_ID"}),
    }
)
public class SongL10n {

    @Id
    @GeneratedValue(generator = "SONG_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SONG_L10N_SEQ_GEN", sequenceName = "SONG_L10N_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Song song;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    public Long getId() {
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

    protected SongL10n() {}

    public SongL10n(String name, Song song, Language language) {
        this.name = name;
        this.song = song;
        this.language = language;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof SongL10n)) return false;

        SongL10n that = (SongL10n) o;

        if (!getLanguage().equals(that.getLanguage())) return false;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        int result = getLanguage().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SongL10n{" +
                "\n id: " + id +
                ";\n song: " + song +
                ";\n language: " + language +
                ";\n name: " + name +
                "}";
    }
}
