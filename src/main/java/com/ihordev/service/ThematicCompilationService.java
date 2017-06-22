package com.ihordev.service;

import com.ihordev.domain.ThematicCompilation;
import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ThematicCompilationService {

    ThematicCompilation findById(Long id);

    Slice<ThematicCompilationAsPageItem> findAllThematicCompilationsAsPageItems(String language,
                                                                                Pageable pageRequest);

    ThematicCompilationAsCurrentMusicEntity findThematicCompilationAsCurrentMusicEntityById(
            Long thematicCompilationId, String language);

    void saveThematicCompilation(ThematicCompilation thematicCompilation);

    ThematicCompilation updateThematicCompilation(ThematicCompilation thematicCompilation);

    void removeThematicCompilation(ThematicCompilation thematicCompilation);

    void removeThematicCompilationById(Long id);
}
