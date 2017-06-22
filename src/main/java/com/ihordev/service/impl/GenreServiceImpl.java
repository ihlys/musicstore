package com.ihordev.service.impl;

import com.ihordev.domain.Genre;
import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import com.ihordev.repository.GenreRepository;
import com.ihordev.service.GenreService;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public Genre findById(Long id) {
        return genreRepository.findOne(id);
    }

    @Override
    public Slice<GenreAsPageItem> findGenresAsPageItemsByParentGenreId(@Nullable Long parentGenreId,
                                                                       String clientLanguage,
                                                                       Pageable pageRequest) {
        return genreRepository.findGenresAsPageItemsByParentGenreId(parentGenreId, clientLanguage, pageRequest);
    }

    @Override
    public GenreAsCurrentMusicEntity findGenreAsCurrentMusicEntityById(Long genreId, String clientLanguage) {
        return genreRepository.findGenreAsCurrentMusicEntityById(genreId, clientLanguage);
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
