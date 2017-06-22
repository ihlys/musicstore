package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.Album;
import com.ihordev.domain.AlbumL10n;
import com.ihordev.domainprojections.AlbumAsCurrentMusicEntity;
import com.ihordev.domainprojections.AlbumAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

public class AlbumRepositoryImpl implements AlbumRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @Override
    public Slice<AlbumAsPageItem> findAlbumsAsPageItemsByArtistId(Long artistId, String language,
                                                                  Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("artistId", artistId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause" , "album.artist.id = :artistId");
        return repositoryQueries.findEntitiesAsPageItems(AlbumAsPageItem.class, Album.class, AlbumL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @Override
    public AlbumAsCurrentMusicEntity findAlbumAsCurrentMusicEntityById(Long albumId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("albumId", albumId);
        queryArgsMap.put("language", language);

        return repositoryQueries.findCurrentMusicEntityById(AlbumAsCurrentMusicEntity.class,
                Album.class, AlbumL10n.class, queryArgsMap);
    }
}
