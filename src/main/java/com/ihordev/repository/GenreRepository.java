package com.ihordev.repository;

import com.ihordev.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import static com.ihordev.domain.Genre.FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING;
import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_IDS_QUERY;
import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUPERGENRES_IDS_QUERY;


@NamedNativeQueries({
        @NamedNativeQuery(
                name = FIND_GENRE_ALL_SUBGENRES_IDS_QUERY,
                query = " WITH all_subgenres (id, parent_genre_id) AS                   " +
                        " (                                                             " +
                        "  SELECT id, parent_genre_id                                   " +
                        "      FROM genre                                               " +
                        "  WHERE id = :genreId                                          " +
                        "                                                               " +
                        "  UNION ALL                                                    " +
                        "                                                               " +
                        "  SELECT childs.id, childs.parent_genre_id                     " +
                        "      FROM all_subgenres parents                               " +
                        "      JOIN genre childs ON parents.id = childs.parent_genre_id " +
                        " )                                                             " +
                        " SELECT id FROM all_subgenres                                  ",
                resultSetMapping = FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING),
        @NamedNativeQuery(
                name = FIND_GENRE_ALL_SUPERGENRES_IDS_QUERY,
                query = " WITH all_supergenres (id, parent_genre_id, branch_order) AS " +
                        " (                                                           " +
                        "  SELECT id, parent_genre_id, 1 AS branch_order              " +
                        "      FROM genre                                             " +
                        "  WHERE id = :genreId                                        " +
                        "                                                             " +
                        "  UNION ALL                                                  " +
                        "                                                             " +
                        "  SELECT parents.id,                                         " +
                        "         parents.parent_genre_id,                            " +
                        "         curr.branch_order + 1 AS branch_order               " +
                        "     FROM all_supergenres curr                               " +
                        "     JOIN genre parents ON curr.parent_genre_id = parents.id " +
                        " )                                                           " +
                        " SELECT id                                                   " +
                        "     FROM all_supergenres                                    " +
                        "     ORDER BY branch_order DESC                              ",
                resultSetMapping = FIND_GENRE_ALL_SUBGENRES_RESULT_MAPPING)
})
public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {

    String FIND_GENRE_ALL_SUBGENRES_IDS_QUERY = "findGenreAllSubGenres";
    String FIND_GENRE_ALL_SUPERGENRES_IDS_QUERY = "findGenreAllSuperGenres";

}
