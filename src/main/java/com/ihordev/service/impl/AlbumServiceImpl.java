package com.ihordev.service.impl;

import com.ihordev.domain.Album;
import com.ihordev.domainprojections.AlbumAsPageItem;
import com.ihordev.repository.AlbumRepository;
import com.ihordev.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public Album findById(Long id) {
        return albumRepository.findOne(id);
    }

    @Override
    public Slice<AlbumAsPageItem> findAlbumsByArtistIdProjectedPaginated(String clientLanguage, Long artistId, Pageable pageRequest) {
        return albumRepository.findAlbumsByArtistIdProjectedPaginated(clientLanguage, artistId, pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveAlbum(Album album) {
        albumRepository.save(album);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeAlbum(Album album) {
        albumRepository.delete(album);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeAlbumById(Long id) {
        albumRepository.delete(id);
    }
}
