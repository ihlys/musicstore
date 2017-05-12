package com.ihordev.service.impl;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.repository.ArtistRepository;
import com.ihordev.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    @Transactional
    public Artist findById(Long id) {
        return artistRepository.findOne(id);
    }

    @Override
    public List<Artist> findByGenre(Long genresId) {
        return null;
    }

    @Override
    @Transactional
    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }

    @Override
    @Transactional
    public Artist updateArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public List<ArtistAsPageItem> findAllPaginated(String language) {
        return artistRepository.findAllPaginated(language);
    }

}