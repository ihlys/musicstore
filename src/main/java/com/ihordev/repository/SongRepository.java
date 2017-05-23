package com.ihordev.repository;

import com.ihordev.domain.Song;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

    @Query(" SELECT song.id AS id,                                           " +
           "        localizedData.name AS name                               " +
           "    FROM Song song                                               " +
           "    LEFT JOIN song.localizedDataSet localizedData                " +
           "    LEFT JOIN localizedData.language lang                        " +
           "    WHERE song.album.id = :albumId                               " +
           "          AND (lang.name = :clientLanguage OR lang.name IS NULL) ")
    Slice<SongAsPageItem> findSongsByAlbumIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                               @Param("albumId") Long albumId,
                                                               Pageable pageRequest);

    @Query(" SELECT song.id AS id,                                        " +
           "        localizedData.name AS name                            " +
           "    FROM Song song                                            " +
           "    LEFT JOIN song.localizedDataSet localizedData             " +
           "    LEFT JOIN localizedData.language lang                     " +
           "    WHERE song.album.id IN (                                  " +
           "                             SELECT album.id AS id            " +
           "                                 FROM Artist artist           " +
           "                                 JOIN artist.albums album     " +
           "                                 WHERE artist.id = :artistId  " +
           "                            )                                 " +
           "       AND (lang.name = :clientLanguage OR lang.name IS NULL) ")
    Slice<SongAsPageItem> findSongsByArtistIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                                @Param("artistId") Long artistId,
                                                                Pageable pageRequest);

}
