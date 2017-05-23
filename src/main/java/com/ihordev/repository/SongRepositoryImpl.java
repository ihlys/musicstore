package com.ihordev.repository;

import com.ihordev.core.jpa.projections.ProjectionFactory;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.ihordev.core.util.JpaUtils.convertRowToPropertiesMap;
import static com.ihordev.core.util.JpaUtils.getOrderByClauseFromSort;
import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_QUERY;
import static java.util.stream.Collectors.toList;


public class SongRepositoryImpl implements SongRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public Slice<SongAsPageItem> findSongsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                                      Pageable pageRequest) {

        Query findGenreSubGenres = em.createNamedQuery(FIND_GENRE_ALL_SUBGENRES_QUERY);
        findGenreSubGenres.setParameter("genreId", genreId);
        List<Long> genresIds = findGenreSubGenres.getResultList();

        String Jpql = String.format(
                      " SELECT song.id AS id,                                                 " +
                      "        localizedData.name AS name                                     " +
                      "     FROM Song song                                                    " +
                      "     LEFT JOIN song.localizedDataSet localizedData                     " +
                      "     LEFT JOIN localizedData.language lang                             " +
                      "     WHERE song.album.id IN (                                          " +
                      "                             SELECT album.id AS id                     " +
                      "                                 FROM Artist artist                    " +
                      "                                 JOIN artist.albums album              " +
                      "                                 WHERE artist.genre.id IN (:genresIds) " +
                      "                            )                                          " +
                      "           AND (lang.name = :clientLanguage OR lang.name IS NULL)      " +
                      "     ORDER BY %s                                                       ",
                getOrderByClauseFromSort(pageRequest.getSort(), ArtistAsPageItem.class));

        Query query = em.createQuery(Jpql);
        query.setParameter("clientLanguage", clientLanguage);
        query.setParameter("genresIds", genresIds);
        query.setFirstResult(pageRequest.getOffset());
        // +1 result is used to determine if there are next slice without additional count query
        int pageSizePlusOne = pageRequest.getPageSize() + 1;
        query.setMaxResults(pageSizePlusOne);

        List<Object[]> resultList = query.getResultList();
        boolean hasNextSliceOfData = resultList.size() == pageSizePlusOne;

        List<SongAsPageItem> songAsPageItemList = resultList.stream()
                .limit(pageRequest.getPageSize())
                .map(this::mapRowToSong)
                .collect(toList());

        return new SliceImpl<>(songAsPageItemList, pageRequest, hasNextSliceOfData);
    }

    private SongAsPageItem mapRowToSong(Object[] row) {
        ProjectionFactory<SongAsPageItem> projectionFactory = new ProjectionFactory<>(SongAsPageItem.class);
        return projectionFactory.createProjection(convertRowToPropertiesMap(row, "id", "name"));
    }
}
