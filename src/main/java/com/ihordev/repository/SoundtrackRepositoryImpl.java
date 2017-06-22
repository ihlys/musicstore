package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.Soundtrack;
import com.ihordev.domain.SoundtrackL10n;
import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SoundtrackRepositoryImpl implements SoundtrackRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @Override
    public Slice<SoundtrackAsPageItem> findAllSoundtracksAsPageItems(String language, Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("language", language);

        return repositoryQueries.findEntitiesAsPageItems(SoundtrackAsPageItem.class, Soundtrack.class,
                SoundtrackL10n.class, Collections.emptyMap(), queryArgsMap, pageRequest);
    }

    @Override
    public SoundtrackAsCurrentMusicEntity findSoundtrackAsCurrentMusicEntityById(Long soundtrackId,
                                                                                 String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("soundtrackId", soundtrackId);
        queryArgsMap.put("language", language);

        return repositoryQueries.findCurrentMusicEntityById(SoundtrackAsCurrentMusicEntity.class, Soundtrack.class,
                SoundtrackL10n.class, queryArgsMap);
    }
}
