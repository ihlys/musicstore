package com.ihordev.service.impl;

import com.ihordev.domain.Genre;
import com.ihordev.domainprojections.GenreAsPageItem;
import com.ihordev.repository.GenreRepository;
import com.ihordev.service.GenreService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Override
    public Genre findById(Long id) {
        return genreRepository.findOne(id);
    }

    @Override
    public Slice<GenreAsPageItem> findGenreSubGenresProjectedPaginated(String clientLanguage, Long genreId,
                                                                       Pageable pageRequest) {
        return genreRepository.findGenreSubGenresProjectedPaginated(clientLanguage, genreId, pageRequest);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeGenre(Genre genre) {
        genreRepository.delete(genre);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeGenreById(Long id) {
        genreRepository.delete(id);
    }
}
