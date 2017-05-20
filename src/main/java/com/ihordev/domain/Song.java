package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Album album;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private Set<SongsLocalizedData> localizedDataSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<SongsLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<SongsLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected Song() {}

    public Song(Set<SongsLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    //TODO: should this entity have areEqual and hashcode?

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(SongsLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
