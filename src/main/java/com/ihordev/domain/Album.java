package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Artist artist;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Collection<Song> songs = new ArrayList<>();

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private Set<AlbumsLocalizedData> localizedDataSet = new HashSet<>();

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

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public Set<AlbumsLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<AlbumsLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected Album() {}

    public Album(String imageSmlUrl, String imageLgUrl) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
    }

    public Album(String imageSmlUrl, String imageLgUrl, Set<AlbumsLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.localizedDataSet = localizedDataSet;
    }

    //TODO: should this entity have equals and hashcode?

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(AlbumsLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
