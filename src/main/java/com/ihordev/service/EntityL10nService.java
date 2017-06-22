package com.ihordev.service;

import com.ihordev.domain.*;

import java.util.List;


public interface EntityL10nService {

    List<GenreL10n>         getAllSuperGenresL10n(Long genreId, String language);

    GenreL10n               getGenreL10n(Long genreId, String language);

    ArtistL10n              getArtistL10n(Long artistId, String language);

    AlbumL10n               getAlbumL10n(Long albumId, String language);

    SongL10n                getSongL10n(Long songId, String language);

    SoundtrackL10n          getSoundtrackL10n(Long soundtrackId, String language);

    ThematicCompilationL10n getThematicCompilationL10n(Long thematicCompilationId, String language);
}
