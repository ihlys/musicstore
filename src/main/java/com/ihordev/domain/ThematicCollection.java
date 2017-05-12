package com.ihordev.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
public class ThematicCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageSmlUrl;

    private String imageLgUrl;

    @OneToMany(mappedBy = "thematicCollection", cascade = CascadeType.ALL)
    private Set<ThematicCollectionLocalizedData> localizedDataSet = new HashSet<>();

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

    public Set<ThematicCollectionLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<ThematicCollectionLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected ThematicCollection() {}

    public ThematicCollection(String imageSmlUrl, String imageLgUrl) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
    }

    public ThematicCollection(String imageSmlUrl, String imageLgUrl,
                              Set<ThematicCollectionLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.localizedDataSet = localizedDataSet;
    }

    //TODO: should this entity have equals and hashcode?

    @Override
    public String toString() {
        return "ThematicCollection{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(ThematicCollectionLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
