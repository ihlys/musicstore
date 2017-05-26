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
    private Set<SongL10n> songL10nSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Set<SongL10n> getSongL10nSet() {
        return songL10nSet;
    }

    public void setSongL10nSet(Set<SongL10n> songL10nSet) {
        this.songL10nSet = songL10nSet;
    }

    protected Song() {}

    public Song(Album album) {
        this(album, new HashSet<>());
    }

    public Song(Album album, Set<SongL10n> songL10nSet) {
        this.album = album;
        this.songL10nSet = songL10nSet;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", songL10nSet=" + songL10nSet.stream()
                    .map(SongL10n::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
