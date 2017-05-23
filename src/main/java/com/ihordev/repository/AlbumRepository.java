package com.ihordev.repository;

import com.ihordev.domain.Album;
import com.ihordev.domainprojections.AlbumAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query(" SELECT album.id AS id,                                       " +
           "        album.imageSmlUrl AS imageSmlUrl,                     " +
           "        album.releaseDate AS releaseDate,                     " +
           "        localizedData.name AS name,                           " +
           "        localizedData.description AS description              " +
           " FROM Album album                                             " +
           " LEFT JOIN album.localizedDataSet localizedData               " +
           " LEFT JOIN localizedData.language lang                        " +
           " WHERE album.artist.id = :artistId                            " +
           "       AND (lang.name = :clientLanguage OR lang.name IS NULL) ")
    Slice<AlbumAsPageItem> findAlbumsByArtistIdProjectedPaginated(@Param("clientLanguage") String clientLanguage,
                                                                  @Param("artistId") Long artistId,
                                                                  Pageable pageRequest);

}
