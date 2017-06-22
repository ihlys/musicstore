package com.ihordev.service;

import com.ihordev.domain.Soundtrack;
import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SoundtrackService {

    Soundtrack findById(Long id);

    Slice<SoundtrackAsPageItem> findAllSoundtracksAsPageItems(String language, Pageable pageRequest);

    SoundtrackAsCurrentMusicEntity findSoundtrackAsCurrentMusicEntityById(Long soundtrackId, String language);

    void saveSoundtrack(Soundtrack soundtrack);

    Soundtrack updateSoundtrack(Soundtrack soundtrack);

    void removeSoundtrack(Soundtrack soundtrack);

    void removeSoundtrackById(Long id);

}
