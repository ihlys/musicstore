package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.Artist;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_IDS_QUERY;


@Repository
public class ArtistRepositoryImpl implements ArtistRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<ArtistAsPageItem> findArtistsAsPageItemsByGenreId(Long genreId, String language,
                                                                   Pageable pageRequest) {
        Query findGenreSubGenresIds = entityManager.createNamedQuery(FIND_GENRE_ALL_SUBGENRES_IDS_QUERY);
        findGenreSubGenresIds.setParameter("genreId", genreId);
        List<Long> genresIds = findGenreSubGenresIds.getResultList();

        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("genresIds", genresIds);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause" , "artist.genre.id IN (:genresIds)");
        return repositoryQueries.findEntitiesAsPageItems(ArtistAsPageItem.class, Artist.class, ArtistL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    public ArtistAsCurrentMusicEntity findArtistAsCurrentMusicEntityById(Long artistId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("artistId", artistId);
        queryArgsMap.put("language", language);

        return repositoryQueries.findCurrentMusicEntityById(ArtistAsCurrentMusicEntity.class,
                Artist.class, ArtistL10n.class, queryArgsMap);
    }
}
