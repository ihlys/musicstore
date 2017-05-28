package com.ihordev.service.impl;

import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.GenreL10n;
import com.ihordev.domain.SongL10n;
import com.ihordev.service.EntityL10nService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import static java.lang.String.format;


@Service
public class EntityL10nServiceImpl implements EntityL10nService {

    @PersistenceContext
    private EntityManager em;

    private final String baseQuery =
            " SELECT l10n                                                 " +
            "     FROM %1$s l10n                                          " +
            "     JOIN l10n.language lang                                 " +
            "     WHERE l10n.%2$s.id = :%3$s                              " +
            "           AND lang.name = :language                         " +
            "           OR lang.name = function('DEFAULT_LANGUAGE',) ";

    private <T> TypedQuery<T> prepareQuery(String entityName, String entityProperty, String entityIdJpql,
                                           Class<T> entityClass, Long entityIdParam, String language) {
        String jpql = format(baseQuery, entityName, entityProperty, entityIdJpql);
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        query.setParameter(entityIdJpql, entityIdParam);
        query.setParameter("language", language);
        return query;
    }

    @Override
    public GenreL10n getGenreL10n(Long genreId, String language) {
        TypedQuery<GenreL10n> query = prepareQuery("GenreL10n", "genre", "genreId",
                GenreL10n.class, genreId, language);
        return query.getSingleResult();
    }

    @Override
    public ArtistL10n getArtistL10n(Long artistId, String language) {
        TypedQuery<ArtistL10n> query = prepareQuery("ArtistL10n", "artist", "artistId",
                ArtistL10n.class, artistId, language);
        return query.getSingleResult();
    }

    @Override
    public AlbumL10n getAlbumL10n(Long albumId, String language) {
        TypedQuery<AlbumL10n> query = prepareQuery("AlbumL10n", "album", "albumId",
                AlbumL10n.class, albumId, language);
        return query.getSingleResult();
    }

    @Override
    public SongL10n getSongL10n(Long songId, String language) {
        TypedQuery<SongL10n> query = prepareQuery("SongL10n", "song", "songId",
                SongL10n.class, songId, language);
        return query.getSingleResult();
    }
}
