package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity(name = "THEMATIC_COMPILATION_LOC_DATA")
@Table(
    uniqueConstraints = {
            @UniqueConstraint(name = "UNQ_TH_COM_L10N_LANG_TH_COM",
                    columnNames = {"LANGUAGE_ID", "THEMATIC_COMPILATION_ID"}),
            @UniqueConstraint(name = "UNQ_TH_COM_L10N_NAME_DESCRIP",
                    columnNames = {"NAME", "DESCRIPTION"})
    }
)
public class ThematicCompilationLocalizedData {

    @Id
    @GeneratedValue(generator = "TH_COM_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "TH_COM_L10N_SEQ_GEN",
            sequenceName = "TH_COM_L10N_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    private ThematicCompilation thematicCompilation;

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

    public ThematicCompilation getThematicCompilation() {
        return thematicCompilation;
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

    protected ThematicCompilationLocalizedData() {}

    public ThematicCompilationLocalizedData(String name, String description, Language language,
                                            ThematicCompilation thematicCompilation) {
        this.name = name;
        this.description = description;
        this.language = language;
        this.thematicCompilation = thematicCompilation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThematicCompilationLocalizedData)) return false;

        ThematicCompilationLocalizedData that = (ThematicCompilationLocalizedData) o;

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
        return "ThematicCompilationLocalizedData{" +
                "id=" + id +
                ", thematicCompilation=" + thematicCompilation +
                ", language=" + language +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
