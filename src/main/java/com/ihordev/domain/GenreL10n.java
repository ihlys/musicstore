package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_GENRE_L10N_LANG_GENRE",
                columnNames = {"LANGUAGE_ID", "GENRE_ID"}),
        @UniqueConstraint(name = "UNQ_GENRE_L10N_NAME_DESCRIP",
                columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class GenreL10n {

    @Id
    @GeneratedValue(generator = "GENRE_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "GENRE_L10N_SEQ_GEN", sequenceName = "GENRE_L10N_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Genre genre;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String name;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false)
    private String description;

    public Long getId() {
        return id;
    }

    public Genre getGenre() {
        return genre;
    }

    public Language getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected GenreL10n() {}

    public GenreL10n(String name, String description, Genre genre, Language language) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.language = language;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof GenreL10n)) return false;

        GenreL10n that = (GenreL10n) o;

        if (!getName().equals(that.getName())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GenreL10n{" +
                "\n id: " + id +
                ";\n genre: " + genre +
                ";\n language: " + language +
                ";\n name: " + name +
                ";\n description: " + description +
                "}";
    }
}
