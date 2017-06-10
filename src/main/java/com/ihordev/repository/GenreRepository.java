package com.ihordev.repository;

import com.ihordev.domain.Genre;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import static com.ihordev.domain.Genre.FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING;
import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_QUERY;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = FIND_GENRE_ALL_SUBGENRES_QUERY,
                query = " WITH all_subgenres (id, parent_genre_id) AS                       " +
                        " (                                                                 " +
                        "  SELECT id, parent_genre_id                                       " +
                        "      FROM genre                                                   " +
                        "  WHERE id = :genreId                                              " +
                        "                                                                   " +
                        "  UNION ALL                                                        " +
                        "                                                                   " +
                        "  SELECT childs.id, childs.parent_genre_id                         " +
                        "      FROM all_subgenres parents                                   " +
                        "      JOIN genre childs ON parents.id = childs.parent_genre_id     " +
                        " )                                                                 " +
                        " SELECT id FROM all_subgenres",
                resultSetMapping = FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING)
})
public interface GenreRepository extends JpaRepository<Genre, Long> {

    String FIND_GENRE_ALL_SUBGENRES_QUERY = "findGenreAllSubGenres";

    @Query(" SELECT genre.id AS id,                                                " +
           "        genre.imageSmlUri AS imageSmlUri,                              " +
           "        l10n.name AS name,                                             " +
           "        l10n.description AS description                                " +
           "     FROM Genre genre                                                  " +
           "     JOIN genre.genreL10nSet l10n                                      " +
           "     JOIN l10n.language lang                                           " +
           "     WHERE :genreId IS NULL AND genre.parentGenre.id IS NULL           " +
           "           OR :genreId IS NOT NULL AND genre.parentGenre.id = :genreId " +
           "           AND (lang.name = :clientLanguage                            " +
           "                OR lang.name = function('DEFAULT_LANGUAGE',))          ")
    Slice<GenreAsPageItem> findSubGenresByParentGenreIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                                          @Param("genreId") Long parentGenreId,
                                                                          Pageable pageRequest);

}
