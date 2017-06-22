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
    public Slice<SongAsPageItem> findSongsAsPageItemsByGenreId(Long genreId, String clientLanguage,
                                                               Pageable pageRequest) {
        return songRepository.findSongsAsPageItemsByGenreId(genreId, clientLanguage, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByArtistId(Long artistId, String clientLanguage,
                                                                Pageable pageRequest) {
        return songRepository.findSongsAsPageItemsByArtistId(artistId, clientLanguage, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByAlbumId(Long albumId, String clientLanguage,
                                                               Pageable pageRequest) {
        return songRepository.findSongsAsPageItemsByAlbumId(albumId, clientLanguage, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsBySoundtrackId(Long soundtrackId, String language,
                                                                    Pageable pageRequest) {
        return songRepository.findSongsAsPageItemsBySoundtrackId(soundtrackId, language, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByThematicCompilationId(Long thematicCompilationId,
                                                                             String language,
                                                                             Pageable pageRequest) {
        return songRepository
                .findSongsAsPageItemsByThematicCompilationId(thematicCompilationId, language, pageRequest);
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
