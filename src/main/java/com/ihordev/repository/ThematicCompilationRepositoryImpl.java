package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.ThematicCompilation;
import com.ihordev.domain.ThematicCompilationL10n;
import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThematicCompilationRepositoryImpl implements ThematicCompilationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @Override
    public Slice<ThematicCompilationAsPageItem> findAllThematicCompilationsAsPageItems(String language,
                                                                                       Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("language", language);

        return repositoryQueries.findEntitiesAsPageItems(ThematicCompilationAsPageItem.class,
                ThematicCompilation.class, ThematicCompilationL10n.class, Collections.emptyMap(),
                queryArgsMap, pageRequest);
    }

    @Override
    public ThematicCompilationAsCurrentMusicEntity findThematicCompilationAsCurrentMusicEntityById(
                Long thematicCompilationId, String language) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("thematicCompilationId", thematicCompilationId);
        queryArgsMap.put("language", language);

        return repositoryQueries.findCurrentMusicEntityById(ThematicCompilationAsCurrentMusicEntity.class,
                ThematicCompilation.class, ThematicCompilationL10n.class, queryArgsMap);
    }
}
