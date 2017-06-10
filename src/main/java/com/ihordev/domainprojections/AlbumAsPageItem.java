package com.ihordev.domainprojections;

import java.time.LocalDate;


public interface AlbumAsPageItem {

    Long      getId();

    String    getImageSmlUri();

    LocalDate getReleaseDate();

    String    getName();

    String    getDescription();
}
