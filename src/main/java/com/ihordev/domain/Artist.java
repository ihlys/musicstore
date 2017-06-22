package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Artist {

    @Id
    @GeneratedValue(generator = "ARTIST_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ARTIST_SEQ_GEN", sequenceName = "ARTIST_SEQ", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    private String imageSmName;

    private String imageLgName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Genre genre;

    @OneToMany(mappedBy = "artist")
    private Set<Album> albums = new HashSet<>();

    @Size(min = 1)
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL)
    private Set<ArtistL10n> artistL10nSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getImageSmName() {
        return imageSmName;
    }

    public void setImageSmName(String imageSmName) {
        this.imageSmName = imageSmName;
    }

    public String getImageLgName() {
        return imageLgName;
    }

    public void setImageLgName(String imageLgName) {
        this.imageLgName = imageLgName;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<ArtistL10n> getArtistL10nSet() {
        return artistL10nSet;
    }

    public void setArtistL10nSet(Set<ArtistL10n> artistL10nSet) {
        this.artistL10nSet = artistL10nSet;
    }

    protected Artist() {}

    public Artist(String imageSmName, String imageLgName, Genre genre) {
        this(imageSmName, imageLgName, genre, new HashSet<>());
    }

    public Artist(String imageSmName, String imageLgName, Genre genre, Set<ArtistL10n> artistL10nSet) {
        this.imageSmName = imageSmName;
        this.imageLgName = imageLgName;
        this.genre = genre;
        this.artistL10nSet = artistL10nSet;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;

        Artist artist = (Artist) o;

        return getArtistL10nSet().equals(artist.getArtistL10nSet());
    }

    @Override
    public int hashCode() {
        return getArtistL10nSet().hashCode();
    }

    @Override
    public String toString() {
        return "Artist{" +
                "\n id=" + id +
                ";\n imageSmName: " + imageSmName +
                ";\n imageLgName: " + imageLgName +
                "}";
    }
}
