package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.ihordev.domain.Genre.FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING;
import static java.util.stream.Collectors.toList;


@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING,
                columns = @ColumnResult(name = "id", type = Long.class))
})
@Entity
public class Genre {

    public static final String FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING = "findGenreAllSubGenresResultMapping";

    @Id
    @GeneratedValue(generator = "GENRE_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GENRE_SEQ_GEN", sequenceName = "GENRE_SEQ", allocationSize = 1)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre parentGenre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentGenre")
    private Set<Genre> subGenres;

    @OneToMany(mappedBy = "genre")
    private Set<Artist> artists;

    @Size(min = 1)
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<GenreLocalizedData> localizedDataSet = new HashSet<>();

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

    public Genre getParentGenre() {
        return parentGenre;
    }

    public void setParentGenre(Genre parentGenre) {
        this.parentGenre = parentGenre;
    }

    public Set<Genre> getSubGenres() {
        return subGenres;
    }

    public void setSubGenres(Set<Genre> subGenres) {
        this.subGenres = subGenres;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<GenreLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<GenreLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected Genre() {}

    public Genre(String imageSmlUrl, String imageLgUrl, Genre parentGenre) {
        this(imageSmlUrl, imageLgUrl, parentGenre, new HashSet<>());
    }

    public Genre(String imageSmlUrl, String imageLgUrl, Genre parentGenre, Set<GenreLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.parentGenre = parentGenre;
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
                    .map(GenreLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
