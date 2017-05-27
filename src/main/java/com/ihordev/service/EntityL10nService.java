package com.ihordev.service;

import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.GenreL10n;
import com.ihordev.domain.SongL10n;


public interface EntityL10nService {

    GenreL10n   getGenreL10n(Long genreId, String language);

    ArtistL10n  getArtistL10n(Long artistId, String language);

    AlbumL10n   getAlbumL10n(Long albumId, String language);

    SongL10n    getSongL10n(Long songId, String language);
}
