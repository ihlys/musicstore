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
    private Set<ThematicCollectionsLocalizedData> localizedDataSet = new HashSet<>();

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

    public Set<ThematicCollectionsLocalizedData> getLocalizedDataSet() {
        return localizedDataSet;
    }

    public void setLocalizedDataSet(Set<ThematicCollectionsLocalizedData> localizedDataSet) {
        this.localizedDataSet = localizedDataSet;
    }

    protected ThematicCollection() {}

    public ThematicCollection(String imageSmlUrl, String imageLgUrl) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
    }

    public ThematicCollection(String imageSmlUrl, String imageLgUrl,
                              Set<ThematicCollectionsLocalizedData> localizedDataSet) {
        this.imageSmlUrl = imageSmlUrl;
        this.imageLgUrl = imageLgUrl;
        this.localizedDataSet = localizedDataSet;
    }

    //TODO: should this entity have areEqual and hashcode?

    @Override
    public String toString() {
        return "ThematicCollection{" +
                "id=" + id +
                ", imageSmlUrl='" + imageSmlUrl + '\'' +
                ", imageLgUrl='" + imageLgUrl + '\'' +
                ", localizedDataSet=" + localizedDataSet.stream()
                    .map(ThematicCollectionsLocalizedData::getLanguage)
                    .map(Language::getName)
                    .collect(toList()) +
                '}';
    }
}
