package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Album {

    public enum AlbumType {
        STUDIO_ALBUM,
        CONCERT_ALBUM,
        COLLECTION
    }

    @Id
    @GeneratedValue(generator = "ALBUM_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ALBUM_SEQ_GEN", sequenceName = "ALBUM_SEQ", allocationSize = 1)
    private Long id;

    private String imageSmlUri;

    private String imageLgUri;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =
            "VARCHAR2(30) CONSTRAINT album_type_ch CHECK(                       " +
            "   album_type IN ('STUDIO_ALBUM', 'CONCERT_ALBUM', 'COLLECTION')   " +
            ")                                                                  ")
    private AlbumType albumType = AlbumType.STUDIO_ALBUM;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private Set<Song> songs;

    @Size(min = 1)
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<AlbumL10n> albumL10nSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getImageSmlUri() {
        return imageSmlUri;
    }

    public void setImageSmlUri(String imageSmlUri) {
        this.imageSmlUri = imageSmlUri;
    }

    public String getImageLgUri() {
        return imageLgUri;
    }

    public void setImageLgUri(String imageLgUri) {
        this.imageLgUri = imageLgUri;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public AlbumType getAlbumType() {
        return albumType;
    }

    public void setAlbumType(AlbumType albumType) {
        this.albumType = albumType;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Set<AlbumL10n> getAlbumL10nSet() {
        return albumL10nSet;
    }

    public void setAlbumL10nSet(Set<AlbumL10n> albumL10nSet) {
        this.albumL10nSet = albumL10nSet;
    }

    protected Album() {}

    public Album(String imageSmlUri, String imageLgUri, LocalDate releaseDate, AlbumType albumType, Artist artist) {
        this(imageSmlUri, imageLgUri, releaseDate, albumType, artist, new HashSet<>());
    }

    public Album(String imageSmlUri, String imageLgUri, LocalDate releaseDate,
                 AlbumType albumType, Artist artist, Set<AlbumL10n> albumL10nSet) {
        this.imageSmlUri = imageSmlUri;
        this.imageLgUri = imageLgUri;
        this.releaseDate = releaseDate;
        this.albumType = albumType;
        this.artist = artist;
        this.albumL10nSet = albumL10nSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;

        Album album = (Album) o;

        return getAlbumL10nSet().equals(album.getAlbumL10nSet());
    }

    @Override
    public int hashCode() {
        return getAlbumL10nSet().hashCode();
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", imageSmlUri='" + imageSmlUri + '\'' +
                ", imageLgUri='" + imageLgUri + '\'' +
                ", albumL10nSet=" + albumL10nSet.stream()
                    .map(AlbumL10n::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
