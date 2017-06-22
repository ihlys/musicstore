package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.ihordev.domain.Genre.FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING;


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
    @Access(AccessType.PROPERTY)
    private Long id;

    private String imageSmName;

    private String imageLgName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Genre parentGenre;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentGenre")
    private Set<Genre> subGenres;

    @OneToMany(mappedBy = "genre")
    private Set<Artist> artists;

    @Size(min = 1)
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private Set<GenreL10n> genreL10nSet = new HashSet<>();

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

    public Set<GenreL10n> getGenreL10nSet() {
        return genreL10nSet;
    }

    public void setGenreL10nSet(Set<GenreL10n> genreL10nSet) {
        this.genreL10nSet = genreL10nSet;
    }

    protected Genre() {}

    public Genre(String imageSmName, String imageLgName, Genre parentGenre) {
        this(imageSmName, imageLgName, parentGenre, new HashSet<>());
    }

    public Genre(String imageSmName, String imageLgName, Genre parentGenre, Set<GenreL10n> genreL10nSet) {
        this.imageSmName = imageSmName;
        this.imageLgName = imageLgName;
        this.parentGenre = parentGenre;
        this.genreL10nSet = genreL10nSet;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;

        Genre genre = (Genre) o;

        return getGenreL10nSet().equals(genre.getGenreL10nSet());
    }

    @Override
    public int hashCode() {
        return getGenreL10nSet().hashCode();
    }

    @Override
    public String toString() {
        return "Genre{" +
                "\n id: " + id +
                ";\n imageSmName: " + imageSmName +
                ";\n imageLgName: " + imageLgName +
                "}";
    }
}
