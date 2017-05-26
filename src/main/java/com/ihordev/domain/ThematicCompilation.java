package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class ThematicCompilation {

    @Id
    @GeneratedValue(generator = "THEMATIC_COM_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "THEMATIC_COM_L10N_SEQ_GEN",
            sequenceName = "THEMATIC_COM_L10N_SEQ", allocationSize = 1)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

    @Size(min = 1)
    @OneToMany(mappedBy = "thematicCompilation", cascade = CascadeType.ALL)
    private Set<ThematicCompilationL10n> thematicCompilationL10nSet = new HashSet<>();

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

    public Set<ThematicCompilationL10n> getThematicCompilationL10nSet() {
        return thematicCompilationL10nSet;
    }

    public void setThematicCompilationL10nSet(Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    protected ThematicCompilation() {}

    public ThematicCompilation(String imageSmlUrl, String imageLgUrl) {
        this(imageSmlUrl, imageLgUrl, new HashSet<>());
    }

    public ThematicCompilation(String imageSmlUrl, String imageLgUrl,
                               Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    //TODO: should this entity have areEqual and hashcode?

    @Override
    public String toString() {
        return "ThematicCompilation{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", thematicCompilationL10nSet=" + thematicCompilationL10nSet.stream()
                    .map(ThematicCompilationL10n::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
