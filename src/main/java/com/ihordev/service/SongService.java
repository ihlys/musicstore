package com.ihordev.service;

import com.ihordev.domain.Song;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface SongService {

    Song findById(Long id);

    Slice<SongAsPageItem> findSongsAsPageItemsByGenreId(Long genreId, String clientLanguage,
                                                        Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByArtistId(Long artistId, String clientLanguage,
                                                         Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByAlbumId(Long albumId, String clientLanguage,
                                                        Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsBySoundtrackId(Long soundtrackId, String language,
                                                             Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByThematicCompilationId(Long thematicCompilationId,
                                                                      String language,
                                                                      Pageable pageRequest);

    void saveSong(Song song);

    Song updateSong(Song song);
    
    void removeSong(Song song);
    
    void removeSongById(Long id);

}
