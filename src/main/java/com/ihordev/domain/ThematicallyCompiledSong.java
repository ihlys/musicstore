package com.ihordev.domain;

import org.checkerframework.checker.nullness.qual.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static com.ihordev.domain.ThematicallyCompiledSong.Id.PRIMARY_KEY_SONG_ID;
import static com.ihordev.domain.ThematicallyCompiledSong.Id.PRIMARY_KEY_THEMATIC_COMPILATION_ID;


@Entity
public class ThematicallyCompiledSong {

    @Embeddable
    public static class Id implements Serializable {

        static final String PRIMARY_KEY_THEMATIC_COMPILATION_ID = "THEMATIC_COMPILATION_ID";
        static final String PRIMARY_KEY_SONG_ID = "SONG_ID";

        @Column(name = PRIMARY_KEY_THEMATIC_COMPILATION_ID)
        private Long thematicCompilationId;

        @Column(name = PRIMARY_KEY_SONG_ID)
        private Long songId;

        public Id() {
        }

        public Id(Long thematicCompilationId, Long songId) {
            this.thematicCompilationId = thematicCompilationId;
            this.songId = songId;
        }

        public Long getThematicCompilationId() {
            return thematicCompilationId;
        }

        public Long getSongId() {
            return songId;
        }

        @Override
        public boolean equals(@Nullable Object o) {
            if (this == o) return true;
            if (!(o instanceof Id)) return false;

            Id id = (Id) o;

            if (!getThematicCompilationId().equals(id.getThematicCompilationId())) return false;
            return getSongId().equals(id.getSongId());

        }

        @Override
        public int hashCode() {
            int result = getThematicCompilationId().hashCode();
            result = 31 * result + getSongId().hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "\n thematicCompilationId: " + thematicCompilationId +
                    ";\n songId: " + songId +
                    "}";
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = PRIMARY_KEY_THEMATIC_COMPILATION_ID,
            insertable = false, updatable = false)
    private ThematicCompilation thematicCompilation;

    @ManyToOne
    @JoinColumn(name = PRIMARY_KEY_SONG_ID,
            insertable = false, updatable = false)
    private Song song;

    @NotNull
    @Column(nullable = false)
    private LocalDate rotationStart;

    @NotNull
    @Column(nullable = false)
    private LocalDate rotationEnd;

    @SuppressWarnings("argument.type.incompatible")
    public ThematicallyCompiledSong(ThematicCompilation thematicCompilation, Song song,
                                    LocalDate rotationStart, LocalDate rotationEnd) {
        this.thematicCompilation = thematicCompilation;
        this.song = song;
        this.id.thematicCompilationId = thematicCompilation.getId();
        this.id.songId = song.getId();
        this.rotationStart = rotationStart;
        this.rotationEnd = rotationEnd;

        this.thematicCompilation.getThematicallyCompiledSongSet().add(this);
    }

    public Id getId() {
        return id;
    }

    public ThematicCompilation getThematicCompilation() {
        return thematicCompilation;
    }

    public Song getSong() {
        return song;
    }

    public LocalDate getRotationStart() {
        return rotationStart;
    }

    public void setRotationStart(LocalDate rotationStart) {
        this.rotationStart = rotationStart;
    }

    public LocalDate getRotationEnd() {
        return rotationEnd;
    }

    public void setRotationEnd(LocalDate rotationEnd) {
        this.rotationEnd = rotationEnd;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (!(o instanceof ThematicallyCompiledSong)) return false;

        ThematicallyCompiledSong that = (ThematicallyCompiledSong) o;

        return getSong().equals(that.getSong());
    }

    @Override
    public int hashCode() {
        return getSong().hashCode();
    }

    @Override
    public String toString() {
        return "ThematicallyCompiledSong{" +
                "\n id: " + id +
                ";\n rotationStart: " + rotationStart +
                ";\n rotationEnd: " + rotationEnd +
                "}";
    }
}
