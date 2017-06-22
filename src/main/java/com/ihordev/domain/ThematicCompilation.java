package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
public class ThematicCompilation {

    @Id
    @GeneratedValue(generator = "THEMATIC_COM_L10N_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "THEMATIC_COM_L10N_SEQ_GEN",
            sequenceName = "THEMATIC_COM_L10N_SEQ", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    private String imageSmName;

    private String imageLgName;

    @OneToMany(mappedBy = "thematicCompilation", cascade = CascadeType.ALL)
    private Set<ThematicallyCompiledSong> thematicallyCompiledSongSet = new HashSet<>();

    @Size(min = 1)
    @OneToMany(mappedBy = "thematicCompilation", cascade = CascadeType.ALL)
    private Set<ThematicCompilationL10n> thematicCompilationL10nSet = new HashSet<>();

    public Long getId() {
        return id;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public String getImageSmName() {
        return imageSmName;
    }

    public void setImageSmName(String imageSmName) {
        this.imageSmName = imageSmName;
    }

    public String getImageLgName() {
        return imageLgName;
    }

    public void setImageLgName(String imageLgName) {
        this.imageLgName = imageLgName;
    }

    public Set<ThematicallyCompiledSong> getThematicallyCompiledSongSet() {
        return thematicallyCompiledSongSet;
    }

    public void setThematicallyCompiledSongSet(Set<ThematicallyCompiledSong> thematicallyCompiledSongSet) {
        this.thematicallyCompiledSongSet = thematicallyCompiledSongSet;
    }

    public Set<ThematicCompilationL10n> getThematicCompilationL10nSet() {
        return thematicCompilationL10nSet;
    }

    public void setThematicCompilationL10nSet(Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    protected ThematicCompilation() {}

    public ThematicCompilation(String imageSmName, String imageLgName) {
        this(imageSmName, imageLgName, new HashSet<>());
    }

    public ThematicCompilation(String imageSmName, String imageLgName,
                               Set<ThematicCompilationL10n> thematicCompilationL10nSet) {
        this.imageSmName = imageSmName;
        this.imageLgName = imageLgName;
        this.thematicCompilationL10nSet = thematicCompilationL10nSet;
    }

    @Override
    public String toString() {
        return "ThematicCompilation{" +
                "\n id: " + id +
                ";\n imageSmName: " + imageSmName +
                ";\n imageLgName: " + imageLgName +
                "}";
    }
}
