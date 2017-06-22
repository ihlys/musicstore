package com.ihordev.service.impl;

import com.ihordev.domain.ThematicCompilation;
import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import com.ihordev.repository.ThematicCompilationRepository;
import com.ihordev.service.ThematicCompilationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public class ThematicCompilationServiceImpl implements ThematicCompilationService {

    @Autowired
    private ThematicCompilationRepository thematicCompilationRepository;

    @Override
    public ThematicCompilation findById(Long id) {
        return thematicCompilationRepository.findOne(id);
    }

    @Override
    public Slice<ThematicCompilationAsPageItem> findAllThematicCompilationsAsPageItems(String language,
                                                                                       Pageable pageRequest) {
        return thematicCompilationRepository.findAllThematicCompilationsAsPageItems(language, pageRequest);
    }

    @Override
    public ThematicCompilationAsCurrentMusicEntity findThematicCompilationAsCurrentMusicEntityById(
            Long thematicCompilationId, String language) {
        return thematicCompilationRepository
                .findThematicCompilationAsCurrentMusicEntityById(thematicCompilationId, language);
    }

    @Override
    public void saveThematicCompilation(ThematicCompilation thematicCompilation) {
        thematicCompilationRepository.save(thematicCompilation);
    }

    @Override
    public ThematicCompilation updateThematicCompilation(ThematicCompilation thematicCompilation) {
        return thematicCompilationRepository.save(thematicCompilation);
    }

    @Override
    public void removeThematicCompilation(ThematicCompilation thematicCompilation) {
        thematicCompilationRepository.delete(thematicCompilation);
    }

    @Override
    public void removeThematicCompilationById(Long id) {
        thematicCompilationRepository.delete(id);
    }
}
