package com.ihordev.service.impl;

import com.ihordev.domain.Soundtrack;
import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
import com.ihordev.repository.SoundtrackRepository;
import com.ihordev.service.SoundtrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SoundtrackServiceImpl implements SoundtrackService {

    @Autowired
    private SoundtrackRepository soundtrackRepository;

    @Override
    public Soundtrack findById(Long id) {
        return soundtrackRepository.findOne(id);
    }

    @Override
    public Slice<SoundtrackAsPageItem> findAllSoundtracksAsPageItems(String language, Pageable pageRequest) {
        return soundtrackRepository.findAllSoundtracksAsPageItems(language, pageRequest);
    }

    @Override
    public SoundtrackAsCurrentMusicEntity findSoundtrackAsCurrentMusicEntityById(Long soundtrackId, String language) {
        return soundtrackRepository.findSoundtrackAsCurrentMusicEntityById(soundtrackId, language);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveSoundtrack(Soundtrack soundtrack) {
        soundtrackRepository.save(soundtrack);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Soundtrack updateSoundtrack(Soundtrack soundtrack) {
        return soundtrackRepository.save(soundtrack);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeSoundtrack(Soundtrack soundtrack) {
        soundtrackRepository.delete(soundtrack);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeSoundtrackById(Long id) {
        soundtrackRepository.delete(id);
    }
}
