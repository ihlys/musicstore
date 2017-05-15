package com.ihordev.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(name = "UNQ_NAME_DESCRIPTION", columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class ThematicCollectionsLocalizedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private ThematicCollection thematicCollection;

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

    protected ThematicCollectionsLocalizedData() {}

    public ThematicCollectionsLocalizedData(String name, String description, Language language,
                                            ThematicCollection thematicCollection) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.thematicCollection = thematicCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThematicCollectionsLocalizedData)) return false;

        ThematicCollectionsLocalizedData that = (ThematicCollectionsLocalizedData) o;

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
        return "ThematicCollectionLocalizedData{" +
                "id=" + id +
                ", thematicCollection=" + thematicCollection +
                ", language=" + language +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
