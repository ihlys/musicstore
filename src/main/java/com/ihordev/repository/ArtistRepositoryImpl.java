package com.ihordev.repository;

import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.ihordev.core.util.JpaUtils.convertRowToPropertiesMap;
import static com.ihordev.core.util.JpaUtils.getOrderByClauseFromSort;
import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_QUERY;
import static java.util.stream.Collectors.toList;


@Repository
public class ArtistRepositoryImpl implements ArtistRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Override
    public Slice<ArtistAsPageItem> findArtistsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                                          Pageable pageRequest) {
        Query findGenreSubGenres = em.createNamedQuery(FIND_GENRE_ALL_SUBGENRES_QUERY);
        findGenreSubGenres.setParameter("genreId", genreId);
        List<Long> genresIds = findGenreSubGenres.getResultList();

        String jpql = String.format(
                      " SELECT artist.id AS id,                                      " +
                      "        artist.imageSmlUrl AS imageSmlUrl,                    " +
                      "        l10n.name AS name,                                    " +
                      "        l10n.description AS description                       " +
                      "     FROM Artist artist                                       " +
                      "     JOIN artist.artistL10nSet l10n                           " +
                      "     JOIN l10n.language lang                                  " +
                      "     WHERE artist.genre.id IN (:genresIds)                    " +
                      "           AND (lang.name = :clientLanguage                   " +
                      "                OR lang.name = function('DEFAULT_LANGUAGE',)) " +
                      "     ORDER BY %s                                              ",
                getOrderByClauseFromSort(pageRequest.getSort(), ArtistAsPageItem.class));

        Query query = em.createQuery(jpql);
        query.setParameter("clientLanguage", clientLanguage);
        query.setParameter("genresIds", genresIds);
        query.setFirstResult(pageRequest.getOffset());
        // +1 result is used to determine if there are next slice without additional count query
        int pageSizePlusOne = pageRequest.getPageSize() + 1;
        query.setMaxResults(pageSizePlusOne);

        List<Object[]> resultList = query.getResultList();
        boolean hasNextSliceOfData = resultList.size() == pageSizePlusOne;

        List<ArtistAsPageItem> artistAsPageItemList = resultList.stream()
                .limit(pageRequest.getPageSize())
                .map(this::mapRowToArtist)
                .collect(toList());

        return new SliceImpl<>(artistAsPageItemList, pageRequest, hasNextSliceOfData);
    }

    private ArtistAsPageItem mapRowToArtist(Object[] row) {
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        return projectionFactory.createProjection(ArtistAsPageItem.class,
                convertRowToPropertiesMap(row, "id", "imageSmlUrl", "name", "description"));
    }
}
