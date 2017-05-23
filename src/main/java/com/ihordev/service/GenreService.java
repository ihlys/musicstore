package com.ihordev.service;

import com.ihordev.domain.Genre;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface GenreService {

    Genre findById(Long id);

    Slice<GenreAsPageItem> findGenreSubGenresProjectedPaginated(String clientLanguage, Long genreId,
                                                                Pageable pageRequest);

    void saveGenre(Genre genre);

    Genre updateGenre(Genre genre);
    
    void removeGenre(Genre genre);
    
    void removeGenreById(Long id);

}
