package com.ihordev.service;

import com.ihordev.domain.Song;

import java.util.List;


public interface SongService {

    Song findById(Long id);

    List<Song> findByGenre(Long genresId);

    List<Song> findByArtist(Long artistsId);

    List<Song> findByAlbum(Long albumsId);

}
