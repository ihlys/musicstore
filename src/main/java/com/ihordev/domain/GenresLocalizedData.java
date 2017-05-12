package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(
    uniqueConstraints = {
            @UniqueConstraint(name = "UNQ_NAME_DESCRIPTION", columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class GenresLocalizedData {

    private static final String GENRE_ID_FK_COLUMN = "GENRE_ID";
    private static final String LANGUAGE_ID_FK_COLUMN = "LANGUAGE_ID";

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = GENRE_ID_FK_COLUMN)
        private Long genresId;

        @Column(name = LANGUAGE_ID_FK_COLUMN)
        private Long languageId;

        protected Id() {}

        public Id(Long genresId, Long languageId) {
            this.genresId = genresId;
            this.languageId = languageId;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!genresId.equals(id.genresId)) return false;
            return languageId.equals(id.languageId);
        }

        @Override
        public int hashCode() {
            int result = genresId.hashCode();
            result = 31 * result + languageId.hashCode();
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = GENRE_ID_FK_COLUMN, insertable = false, updatable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = LANGUAGE_ID_FK_COLUMN, insertable = false, updatable = false)
    private Language language;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String description;

    public Id getId() {
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
        this.id.genresId = genre.getId();
        this.id.languageId = language.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenresLocalizedData)) return false;

        GenresLocalizedData that = (GenresLocalizedData) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
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
