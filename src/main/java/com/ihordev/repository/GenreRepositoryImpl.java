package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.Genre;
import com.ihordev.domain.GenreL10n;
import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class GenreRepositoryImpl implements GenreRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    public Slice<GenreAsPageItem> findGenresAsPageItemsByParentGenreId(@Nullable Long parentGenreId, String language,
                                                                       Pageable pageRequest) {
        Map<String, @Nullable Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("parentGenreId", parentGenreId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause",
                " :parentGenreId IS NULL AND genre.parentGenre.id IS NULL                 " +
                " OR :parentGenreId IS NOT NULL AND genre.parentGenre.id = :parentGenreId ");
        return repositoryQueries.findEntitiesAsPageItems(GenreAsPageItem.class, Genre.class, GenreL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @Override
    public GenreAsCurrentMusicEntity findGenreAsCurrentMusicEntityById(Long genreId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("genreId", genreId);
        queryArgsMap.put("language", language);

        return repositoryQueries.findCurrentMusicEntityById(GenreAsCurrentMusicEntity.class,
                Genre.class, GenreL10n.class, queryArgsMap);
    }
}
