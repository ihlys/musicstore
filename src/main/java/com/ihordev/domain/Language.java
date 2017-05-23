package com.ihordev.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Language {

    @Id
    @GeneratedValue(generator = "LANGUAGE_SEQ_GEN", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "LANGUAGE_SEQ_GEN", sequenceName = "LANGUAGE_SEQ", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(nullable = false, unique = true)
    private String name;

    protected Language() {}

    public Language(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
