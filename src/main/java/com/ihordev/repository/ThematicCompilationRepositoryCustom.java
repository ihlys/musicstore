package com.ihordev.repository;

import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ThematicCompilationRepositoryCustom {

    Slice<ThematicCompilationAsPageItem> findAllThematicCompilationsAsPageItems(String language,
                                                                                Pageable pageRequest);

    ThematicCompilationAsCurrentMusicEntity findThematicCompilationAsCurrentMusicEntityById(Long artistId,
                                                                                            String language);
}
