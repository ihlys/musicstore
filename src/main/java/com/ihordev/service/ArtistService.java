package com.ihordev.service;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;

import java.util.List;


public interface ArtistService {

    Artist findById(Long id);

    List<Artist> findByGenre(Long genresId);

    void saveArtist(Artist artist);

    Artist updateArtist(Artist artist);

    List<ArtistAsPageItem> findAllPaginated(String language);
}
