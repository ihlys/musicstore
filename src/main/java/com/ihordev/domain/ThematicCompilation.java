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
    private Set<ThematicCompilationLocalizedData> localizedDataSet = new HashSet<>();

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

    public Set<ThematicCompilationLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<ThematicCompilationLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected ThematicCompilation() {}

    public ThematicCompilation(String imageSmlUrl, String imageLgUrl) {
        this(imageSmlUrl, imageLgUrl, new HashSet<>());
    }

    public ThematicCompilation(String imageSmlUrl, String imageLgUrl,
                               Set<ThematicCompilationLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.localizedDataSet = localizedDataSet;
    }

    //TODO: should this entity have areEqual and hashcode?

    @Override
    public String toString() {
        return "ThematicCompilation{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(ThematicCompilationLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
