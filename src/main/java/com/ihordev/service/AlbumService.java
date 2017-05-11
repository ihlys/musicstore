package com.ihordev.service;

import com.ihordev.domain.Album;

import java.util.List;


public interface AlbumService {

    Album findById(Long id);

    List<Album> findByArtist(Long artistsId);
}
