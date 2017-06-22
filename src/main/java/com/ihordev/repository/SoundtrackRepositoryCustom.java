package com.ihordev.repository;

import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SoundtrackRepositoryCustom {

    Slice<SoundtrackAsPageItem> findAllSoundtracksAsPageItems(String language, Pageable pageRequest);

    SoundtrackAsCurrentMusicEntity findSoundtrackAsCurrentMusicEntityById(Long artistId, String language);
}
