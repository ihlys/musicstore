package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Soundtrack {

    @Id
    @GeneratedValue(generator = "SOUNDTRACK_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SOUNDTRACK_SEQ_GEN", sequenceName = "SOUNDTRACK_SEQ", allocationSize = 1)
    @Access(AccessType.PROPERTY)
    private Long id;

    private String imageSmName;

    private String imageLgName;

    @NotNull
    @Column(nullable = false)
    private LocalDate releaseDate;

    @ManyToMany
    @JoinTable(
        name = "SOUNDTRACK_SONG",
        joinColumns = @JoinColumn(name = "SOUNDTRACK_ID"),
        inverseJoinColumns = @JoinColumn(name = "SONG_ID")
    )
    private Set<Song> songs;

    @Size(min = 1)
    @OneToMany(mappedBy = "soundtrack", cascade = CascadeType.ALL)
    private Set<SoundtrackL10n> soundtrackL10nSet = new HashSet<>();

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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Set<SoundtrackL10n> getSoundtrackL10nSet() {
        return soundtrackL10nSet;
    }

    public void setSoundtrackL10nSet(Set<SoundtrackL10n> soundtrackL10nSet) {
        this.soundtrackL10nSet = soundtrackL10nSet;
    }

    protected Soundtrack() {}

    public Soundtrack(String imageSmName, String imageLgName, LocalDate releaseDate) {
        this(imageSmName, imageLgName, releaseDate, new HashSet<>());
    }

    public Soundtrack(String imageSmName, String imageLgName,
                 LocalDate releaseDate, Set<SoundtrackL10n> soundtrackL10nSet) {
        this.imageSmName = imageSmName;
        this.imageLgName = imageLgName;
        this.releaseDate = releaseDate;
        this.soundtrackL10nSet = soundtrackL10nSet;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof Soundtrack)) return false;

        Soundtrack soundtrack = (Soundtrack) o;

        return getSoundtrackL10nSet().equals(soundtrack.getSoundtrackL10nSet());
    }

    @Override
    public int hashCode() {
        return getSoundtrackL10nSet().hashCode();
    }

    @Override
    public String toString() {
        return "Soundtrack{" +
                "\n id: " + id +
                ";\n imageSmName: " + imageSmName +
                ";\n imageLgName: " + imageLgName +
                "}";
    }
}
