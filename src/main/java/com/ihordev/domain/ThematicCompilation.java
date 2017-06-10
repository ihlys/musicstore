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

    private String imageSmlUri;

    private String imageLgUri;

    @Size(min = 1)
    @OneToMany(mappedBy = "thematicCompilation", cascade = CascadeType.ALL)
    private Set<ThematicCompilationL10n> thematicCompilationL10nSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getImageSmlUri() {
        return imageSmlUri;
    }

    public void setImageSmlUri(String imageSmlUri) {
        this.imageSmlUri = imageSmlUri;
    }

    public String getImageLgUri() {
        return imageLgUri;
    }

    public void setImageLgUri(String imageLgUri) {
        this.imageLgUri = imageLgUri;
    }

    public Set<ThematicCompilationL10n> getThematicCompilationL10nSet() {
        return thematicCompilationL10nSet;
    }

    public void setThematicCompilationL10nSet(Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    protected ThematicCompilation() {}

    public ThematicCompilation(String imageSmlUri, String imageLgUri) {
        this(imageSmlUri, imageLgUri, new HashSet<>());
    }

    public ThematicCompilation(String imageSmlUri, String imageLgUri,
                               Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.imageSmlUri = imageSmlUri;
        this.imageLgUri = imageLgUri;
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    @Override
    public String toString() {
        return "ThematicCompilation{" +
                "id=" + id +
                ", imageSmlUri='" + imageSmlUri + '\'' +
                ", imageLgUri='" + imageLgUri + '\'' +
                ", thematicCompilationL10nSet=" + thematicCompilationL10nSet.stream()
                    .map(ThematicCompilationL10n::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
