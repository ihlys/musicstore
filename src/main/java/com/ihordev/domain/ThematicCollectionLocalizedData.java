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
public class ThematicCollectionLocalizedData {

    private static final String THEMATIC_COLLECTION_ID_FK_COLUMN = "THEMATIC_COLLECTION_ID";
    private static final String LANGUAGE_ID_FK_COLUMN = "LANGUAGE_ID";

    @Embeddable
    public static class Id implements Serializable {

        @Column(name = THEMATIC_COLLECTION_ID_FK_COLUMN)
        private Long thematicCollectionId;

        @Column(name = LANGUAGE_ID_FK_COLUMN)
        private Long languageId;

        protected Id() {}

        public Id(Long thematicCollectionId, Long languageId) {
            this.thematicCollectionId = thematicCollectionId;
            this.languageId = languageId;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!thematicCollectionId.equals(id.thematicCollectionId)) return false;
            return languageId.equals(id.languageId);
        }

        @Override
        public int hashCode() {
            int result = thematicCollectionId.hashCode();
            result = 31 * result + languageId.hashCode();
            return result;
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = THEMATIC_COLLECTION_ID_FK_COLUMN, insertable = false, updatable = false)
    private ThematicCollection thematicCollection;

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

    public ThematicCollection getThematicCollection() {
        return thematicCollection;
    }

    public Language getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected ThematicCollectionLocalizedData() {}

    public ThematicCollectionLocalizedData(String name, String description,
                                           ThematicCollection thematicCollection,
                                           Language language) {
        this.name = name;
        this.description = description;

        this.thematicCollection = thematicCollection;
        this.language = language;
        this.id.thematicCollectionId = thematicCollection.getId();
        this.id.languageId = language.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThematicCollectionLocalizedData)) return false;

        ThematicCollectionLocalizedData that = (ThematicCollectionLocalizedData) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "ThematicCollectionLocalizedData{" +
                "id=" + id +
                ", thematicCollection=" + thematicCollection +
                ", language=" + language +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
