package com.ihordev.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre parentGenre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentGenre")
    private Set<Genre> subGenres;

/*    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<Artist> artists;*/

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<GenresLocalizedData> localizedDataSet = new HashSet<>();

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

/*    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }*/

    public Set<GenresLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<GenresLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected Genre() {}

    public Genre(String imageSmlUrl, String imageLgUrl) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
    }

    public Genre(String imageSmlUrl, String imageLgUrl, Set<GenresLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.localizedDataSet = localizedDataSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        return getLocalizedDataSet().equals(genre.getLocalizedDataSet());
    }

    @Override
    public int hashCode() {
        return getLocalizedDataSet().hashCode();
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(GenresLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
