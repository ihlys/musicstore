package com.ihordev.repository;

import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GenreRepositoryCustom {


    Slice<GenreAsPageItem> findGenresAsPageItemsByParentGenreId(@Nullable Long parentGenreId, String language,
                                                                Pageable pageRequest);

    GenreAsCurrentMusicEntity findGenreAsCurrentMusicEntityById(Long genreId, String language);

}
