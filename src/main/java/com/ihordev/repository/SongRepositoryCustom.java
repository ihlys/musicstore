package com.ihordev.repository;

import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface SongRepositoryCustom {

    Slice<SongAsPageItem> findSongsAsPageItemsByAlbumId(Long albumId, String language,
                                                        Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByArtistId(Long artistId, String language,
                                                         Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByGenreId(Long genreId, String language,
                                                        Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsBySoundtrackId(Long soundtrackId, String language,
                                                             Pageable pageRequest);

    Slice<SongAsPageItem> findSongsAsPageItemsByThematicCompilationId(Long thematicCompilationId,
                                                                      String language,
                                                                      Pageable pageRequest);
}
