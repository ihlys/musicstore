package com.ihordev.service;

import com.ihordev.domain.Genre;

import java.util.List;


public interface GenreService {

    Genre findById(Long id);

    List<Genre> findAllGenres();

    List<Genre> findSubGenres(Long id);
}
