package com.ihordev.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_LANGUAGE_GENRE", columnNames = {"LANGUAGE", "GENRE"}),
        @UniqueConstraint(name = "UNQ_NAME_DESCRIPTION", columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class GenresLocalizedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private Genre genre;

    @NotNull
    @ManyToOne(optional = false)
    private Language language;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
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

    protected GenresLocalizedData() {}

    public GenresLocalizedData(String name, String description, Genre genre, Language language) {
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenresLocalizedData)) return false;

        GenresLocalizedData that = (GenresLocalizedData) o;

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
        return "GenresLocalizedData{" +
                "id=" + id +
                ", genre=" + genre +
                ", language=" + language +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
