package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Song {

    @Id
    @GeneratedValue(generator = "SONG_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SONG_SEQ_GEN", sequenceName = "SONG_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Album album;

    @Size(min = 1)
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private Set<SongLocalizedData> localizedDataSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<SongLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<SongLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    public Song(Album album) {
        this(album, new HashSet<>());
    }

    public Song(Album album, Set<SongLocalizedData> localizedDataSet) {
        this.album = album;
        this.localizedDataSet = localizedDataSet;
    }

    protected Song() {}

    public Song(Set<SongLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(SongLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
