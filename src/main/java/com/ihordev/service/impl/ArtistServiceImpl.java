package com.ihordev.service.impl;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.repository.ArtistRepository;
import com.ihordev.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public Artist findById(Long id) {
        return artistRepository.findOne(id);
    }

    @Override
    public List<Artist> findByGenre(Long genresId) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveArtist(Artist artist) {
        artistRepository.save(artist);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Artist updateArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    @Override
    public List<ArtistAsPageItem> findAllPaginated(String language) {
        //return artistRepository.findAllPaginated(language);
        throw new AssertionError("not implemented yet.");
    }

}
