package com.ihordev.service.impl;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.*;
import com.ihordev.service.EntityL10nService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUPERGENRES_IDS_QUERY;
import static java.util.stream.Collectors.toList;


@Service
public class EntityL10nServiceImpl implements EntityL10nService {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GenreL10n> getAllSuperGenresL10n(Long genreId, String language) {
        Query findGenreSuperGenres = entityManager.createNamedQuery(FIND_GENRE_ALL_SUPERGENRES_IDS_QUERY);
        findGenreSuperGenres.setParameter("genreId", genreId);
        List<Long> genresIds = findGenreSuperGenres.getResultList();

        return genresIds.stream()
                .map(superGenreId -> getGenreL10n(superGenreId, language))
                .collect(toList());
    }

    @Override
    public GenreL10n getGenreL10n(Long genreId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("genreId", genreId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(GenreL10n.class, Genre.class, queryArgsMap);
    }

    @Override
    public ArtistL10n getArtistL10n(Long artistId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("artistId", artistId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(ArtistL10n.class, Artist.class, queryArgsMap);
    }

    @Override
    public AlbumL10n getAlbumL10n(Long albumId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("albumId", albumId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(AlbumL10n.class, Album.class, queryArgsMap);
    }

    @Override
    public SongL10n getSongL10n(Long songId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("songId", songId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(SongL10n.class, Song.class, queryArgsMap);
    }

    @Override
    public SoundtrackL10n getSoundtrackL10n(Long soundtrackId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("soundtrackId", soundtrackId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(SoundtrackL10n.class, Soundtrack.class, queryArgsMap);
    }

    @Override
    public ThematicCompilationL10n getThematicCompilationL10n(Long thematicCompilationId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("thematicCompilationId", thematicCompilationId);
        queryArgsMap.put("language", language);
        return repositoryQueries.findEntityL10n(ThematicCompilationL10n.class,
                ThematicCompilation.class, queryArgsMap);
    }
}
