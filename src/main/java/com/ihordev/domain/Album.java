package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


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
    @Access(AccessType.PROPERTY)
    private Long id;

    private String imageSmName;

    private String imageLgName;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition =
            "VARCHAR2(30) CONSTRAINT album_type_ch CHECK(                     " +
            "   album_type IN ('STUDIO_ALBUM', 'CONCERT_ALBUM', 'COLLECTION') " +
            ")                                                                ")
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

    public Album(String imageSmName, String imageLgName, LocalDate releaseDate, AlbumType albumType, Artist artist) {
        this(imageSmName, imageLgName, releaseDate, albumType, artist, new HashSet<>());
    }

    public Album(String imageSmName, String imageLgName, LocalDate releaseDate,
                 AlbumType albumType, Artist artist, Set<AlbumL10n> albumL10nSet) {
        this.imageSmName = imageSmName;
        this.imageLgName = imageLgName;
        this.releaseDate = releaseDate;
        this.albumType = albumType;
        this.artist = artist;
        this.albumL10nSet = albumL10nSet;
    }

    @Override
    public boolean equals(@Nullable Object o) {
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
                "\n id: " + id +
                ";\n imageSmName: " + imageSmName +
                ";\n imageLgName: " + imageLgName +
                '}';
    }
}
