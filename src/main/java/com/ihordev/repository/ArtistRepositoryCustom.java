package com.ihordev.repository;

import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ArtistRepositoryCustom {

    Slice<ArtistAsPageItem> findArtistsAsPageItemsByGenreId(Long genreId, String language,
                                                            Pageable pageRequest);

    ArtistAsCurrentMusicEntity findArtistAsCurrentMusicEntityById(Long artistId, String language);

}
