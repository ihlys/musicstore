package com.ihordev.repository;

import com.ihordev.core.repositories.RepositoryQueries;
import com.ihordev.domain.Song;
import com.ihordev.domain.SongL10n;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.repository.GenreRepository.FIND_GENRE_ALL_SUBGENRES_IDS_QUERY;


public class SongRepositoryImpl implements SongRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private RepositoryQueries repositoryQueries;

    @PostConstruct
    public void init() {
        this.repositoryQueries = new RepositoryQueries(entityManager);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByAlbumId(Long albumId, String language,
                                                               Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("albumId", albumId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause", "song.album.id = :albumId");
        return repositoryQueries.findEntitiesAsPageItems(SongAsPageItem.class, Song.class, SongL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByArtistId(Long artistId, String language,
                                                                Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("artistId", artistId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause", "song.artist.id  = :artistId");
        return repositoryQueries.findEntitiesAsPageItems(SongAsPageItem.class, Song.class, SongL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByGenreId(Long genreId, String language,
                                                               Pageable pageRequest) {
        Query findGenreSubGenres = entityManager.createNamedQuery(FIND_GENRE_ALL_SUBGENRES_IDS_QUERY);
        findGenreSubGenres.setParameter("genreId", genreId);
        List<Long> genresIds = findGenreSubGenres.getResultList();

        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("genresIds", genresIds);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("whereClause",
                "song.artist.id IN (                                         " +
                "                   SELECT artist.id AS id                   " +
                "                       FROM Artist artist                   " +
                "                       WHERE artist.genre.id IN (:genresIds)" +
                "                  )                                         ");
        return repositoryQueries.findEntitiesAsPageItems(SongAsPageItem.class, Song.class, SongL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsBySoundtrackId(Long soundtrackId, String language,
                                                                    Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("soundtrackId", soundtrackId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("fromClauseEnd", "JOIN song.soundtracks soundtrack");
        customClausesMap.put("whereClause", "soundtrack.id = :soundtrackId");

        return repositoryQueries.findEntitiesAsPageItems(SongAsPageItem.class, Song.class, SongL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }

    @Override
    public Slice<SongAsPageItem> findSongsAsPageItemsByThematicCompilationId(Long thematicCompilationId,
                                                                             String language,
                                                                             Pageable pageRequest) {
        Map<String, Object> queryArgsMap = new HashMap<>();
        queryArgsMap.put("thematicCompilationId", thematicCompilationId);
        queryArgsMap.put("language", language);

        Map<String, String> customClausesMap = new HashMap<>();
        customClausesMap.put("fromClauseStart", " ThematicallyCompiledSong thSong " +
                                                " JOIN thSong.song song           ");
        customClausesMap.put("whereClause", "thSong.thematicCompilation.id = :thematicCompilationId");

        return repositoryQueries.findEntitiesAsPageItems(SongAsPageItem.class, Song.class, SongL10n.class,
                customClausesMap, queryArgsMap, pageRequest);
    }
}
