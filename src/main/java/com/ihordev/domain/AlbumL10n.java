package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_ALBUM_L10N_LANG_ALBUM",
                columnNames = {"LANGUAGE_ID", "ALBUM_ID"}),
        @UniqueConstraint(name = "UNQ_ALBUM_L10N_NAME_DESCRIP",
                columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class AlbumL10n {

    @Id
    @GeneratedValue(generator = "ALBUM_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ALBUM_L10N_SEQ_GEN", sequenceName = "ALBUM_L10N_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Album album;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(nullable = false)
    private String description;

    public Long getId() {
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

    protected AlbumL10n() {}

    public AlbumL10n(String name, String description, Album album, Language language) {
        this.name = name;
        this.description = description;
        this.album = album;
        this.language = language;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof AlbumL10n)) return false;

        AlbumL10n that = (AlbumL10n) o;

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
        return "AlbumL10n{" +
                "\n id: " + id +
                ";\n language: " + language +
                ";\n album: " + album +
                ";\n name: " + name +
                ";\n description: " + description +
                "}";
    }
}
