package com.ihordev.service;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.AlbumAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ArtistService {

    Artist findById(Long id);

    Slice<ArtistAsPageItem> findArtistsAsPageItemsByGenreId(Long genreId, String clientLanguage,
                                                            Pageable pageRequest);

    ArtistAsCurrentMusicEntity findArtistAsCurrentMusicEntityById(Long artistId, String clientLanguage);

    void saveArtist(Artist artist);

    Artist updateArtist(Artist artist);

    void removeArtist(Artist artist);

    void removeArtistById(Long id);

}
