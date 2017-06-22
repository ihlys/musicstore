package com.ihordev.domainprojections;

import java.time.LocalDate;


public interface AlbumAsPageItem extends AbstractPageItem{

    LocalDate getReleaseDate();
}
