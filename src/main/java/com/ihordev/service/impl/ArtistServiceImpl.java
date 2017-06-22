package com.ihordev.service.impl;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.repository.ArtistRepository;
import com.ihordev.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ArtistServiceImpl implements ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public Artist findById(Long id) {
        return artistRepository.findOne(id);
    }

    @Override
    public Slice<ArtistAsPageItem> findArtistsAsPageItemsByGenreId(Long genreId, String clientLanguage,
                                                                   Pageable pageRequest) {
        return artistRepository.findArtistsAsPageItemsByGenreId(genreId, clientLanguage, pageRequest);
    }

    @Override
    public ArtistAsCurrentMusicEntity findArtistAsCurrentMusicEntityById(Long artistId, String clientLanguage) {
        return artistRepository.findArtistAsCurrentMusicEntityById(artistId, clientLanguage);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeArtist(Artist artist) {
        artistRepository.delete(artist);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeArtistById(Long id) {
        artistRepository.delete(id);
    }

}
