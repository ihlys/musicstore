package com.ihordev.service;

import com.ihordev.domain.Artist;

import java.util.List;


public interface ArtistService {

    Artist findById(Long id);

    List<Artist> findByGenre(Long genresId);
}
