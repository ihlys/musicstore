package com.ihordev.service;

import com.ihordev.domain.Album;
import com.ihordev.domainprojections.AlbumAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface AlbumService {

    Album findById(Long id);

    Slice<AlbumAsPageItem> findAlbumsByArtistIdProjectedPaginated(String clientLanguage, Long artistId,
                                                                  Pageable pageRequest);

    void saveAlbum(Album album);

    Album updateAlbum(Album album);

    void removeAlbum(Album album);

    void removeAlbumById(Long id);

}
