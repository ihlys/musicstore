package com.ihordev.repository;

import com.ihordev.domainprojections.AlbumAsCurrentMusicEntity;
import com.ihordev.domainprojections.AlbumAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AlbumRepositoryCustom {

    Slice<AlbumAsPageItem> findAlbumsAsPageItemsByArtistId(Long artistId, String language,
                                                           Pageable pageRequest);

    AlbumAsCurrentMusicEntity findAlbumAsCurrentMusicEntityById(Long albumId, String language);

}
