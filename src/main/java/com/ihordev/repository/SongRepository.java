package com.ihordev.repository;

import com.ihordev.domain.Song;
import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SongRepository extends JpaRepository<Song, Long>, SongRepositoryCustom {

    @Query(" SELECT song.id AS id,                    " +
           "        l10n.name AS name                 " +
           "    FROM Song song                        " +
           "    JOIN song.songL10nSet l10n            " +
           "    JOIN l10n.language lang               " +
           "    WHERE song.album.id = :albumId        " +
           "          AND lang.name = :clientLanguage ")
    Slice<SongAsPageItem> findSongsByAlbumIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                               @Param("albumId") Long albumId,
                                                               Pageable pageRequest);

    @Query(" SELECT song.id AS id,                                        " +
           "        l10n.name AS name                                     " +
           "    FROM Song song                                            " +
           "    JOIN song.songL10nSet l10n                                " +
           "    JOIN l10n.language lang                                   " +
           "    WHERE song.album.id IN (                                  " +
           "                             SELECT album.id AS id            " +
           "                                 FROM Artist artist           " +
           "                                 JOIN artist.albums album     " +
           "                                 WHERE artist.id = :artistId  " +
           "                            )                                 " +
           "       AND lang.name = :clientLanguage                        ")
    Slice<SongAsPageItem> findSongsByArtistIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                                @Param("artistId") Long artistId,
                                                                Pageable pageRequest);

}
