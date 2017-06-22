package com.ihordev.service;

import com.ihordev.domain.Genre;
import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface GenreService {

    Genre findById(Long id);

    Slice<GenreAsPageItem> findGenresAsPageItemsByParentGenreId(@Nullable Long parentGenreId,
                                                                String clientLanguage,
                                                                Pageable pageRequest);

    GenreAsCurrentMusicEntity findGenreAsCurrentMusicEntityById(Long genreId, String clientLanguage);

    void saveGenre(Genre genre);

    Genre updateGenre(Genre genre);
    
    void removeGenre(Genre genre);
    
    void removeGenreById(Long id);

}
