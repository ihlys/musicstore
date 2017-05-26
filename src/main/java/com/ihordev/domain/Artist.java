package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Artist {

    @Id
    @GeneratedValue(generator = "ARTIST_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ARTIST_SEQ_GEN", sequenceName = "ARTIST_SEQ", allocationSize = 1)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

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

    public String getImageSmlUrl() {
        return imageSmlUrl;
    }

    public void setImageSmlUrl(String imageSmlUrl) {
        this.imageSmlUrl = imageSmlUrl;
    }

    public String getImageLgUrl() {
        return imageLgUrl;
    }

    public void setImageLgUrl(String imageLgUrl) {
        this.imageLgUrl = imageLgUrl;
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

    public Artist(String imageSmlUrl, String imageLgUrl, Genre genre) {
        this(imageSmlUrl, imageLgUrl, genre, new HashSet<>());
    }

    public Artist(String imageSmlUrl, String imageLgUrl, Genre genre, Set<ArtistL10n> artistL10nSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.genre = genre;
        this.artistL10nSet = artistL10nSet;
    }

    @Override
    public boolean equals(Object o) {
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
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", artistL10nSet=" + artistL10nSet.stream()
                    .map(ArtistL10n::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
