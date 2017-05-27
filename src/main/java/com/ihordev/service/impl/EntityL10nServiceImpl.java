package com.ihordev.service.impl;

import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.GenreL10n;
import com.ihordev.domain.SongL10n;
import com.ihordev.service.EntityL10nService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class EntityL10nServiceImpl implements EntityL10nService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public GenreL10n getGenreL10n(Long genreId, String language) {
        String jpql = " SELECT l10n                                             " +
                      "     FROM GenreL10n l10n                                 " +
                      "     JOIN l10n.language lang                             " +
                      "     WHERE l10n.genre.id = :genreId                      " +
                      "           AND lang.name = :language OR lang.name = 'en' ";
        //em.createQuery();
        return null;
    }

    @Override
    public ArtistL10n getArtistL10n(Long genreId, String language) {
        return null;
    }

    @Override
    public AlbumL10n getAlbumL10n(Long genreId, String language) {
        return null;
    }

    @Override
    public SongL10n getSongL10n(Long genreId, String language) {
        return null;
    }
}
