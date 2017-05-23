package com.ihordev.service;

import com.ihordev.domain.Song;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface SongService {

    Song findById(Long id);

    Slice<SongAsPageItem> findSongsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                               Pageable pageRequest);

    Slice<SongAsPageItem> findSongsByArtistIdProjectedPaginated(String clientLanguage, Long artistId,
                                                                Pageable pageRequest);

    Slice<SongAsPageItem> findSongsByAlbumIdProjectedPaginated(String clientLanguage, Long albumId,
                                                               Pageable pageRequest);

    void saveSong(Song song);

    Song updateSong(Song song);
    
    void removeSong(Song song);
    
    void removeSongById(Long id);

}
