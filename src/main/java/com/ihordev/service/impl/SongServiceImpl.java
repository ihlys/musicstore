package com.ihordev.service.impl;

import com.ihordev.domain.Song;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.repository.SongRepository;
import com.ihordev.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SongServiceImpl implements SongService {

    @Autowired
    private SongRepository songRepository;


    @Override
    public Song findById(Long id) {
        return songRepository.findOne(id);
    }

    @Override
    public Slice<SongAsPageItem> findSongsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                                      Pageable pageRequest) {
        return songRepository.findSongsByGenreIdProjectedPaginated(clientLanguage, genreId, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsByArtistIdProjectedPaginated(String clientLanguage, Long artistId,
                                                                       Pageable pageRequest) {
        return songRepository.findSongsByArtistIdProjectedPaginated(clientLanguage, artistId, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsByAlbumIdProjectedPaginated(String clientLanguage, Long albumId,
                                                                      Pageable pageRequest) {
        return songRepository.findSongsByAlbumIdProjectedPaginated(clientLanguage, albumId, pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSong(Song song) {
        songRepository.save(song);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Song updateSong(Song song) {
        return songRepository.save(song);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeSong(Song song) {
        songRepository.delete(song);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeSongById(Long id) {
        songRepository.delete(id);
    }
}
