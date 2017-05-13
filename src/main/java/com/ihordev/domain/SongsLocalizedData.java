package com.ihordev.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_LANGUAGE_SONG", columnNames = {"LANGUAGE", "SONG"}),
    }
)
public class SongsLocalizedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Song song;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotEmpty
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

    protected SongsLocalizedData() {}

    public SongsLocalizedData(String name, Song song, Language language) {
        this.name = name;
        this.song = song;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SongsLocalizedData)) return false;

        SongsLocalizedData that = (SongsLocalizedData) o;

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
        return "SongsLocalizedData{" +
                "id=" + id +
                ", song=" + song +
                ", language=" + language +
                ", name='" + name + '\'' +
                '}';
    }
}
