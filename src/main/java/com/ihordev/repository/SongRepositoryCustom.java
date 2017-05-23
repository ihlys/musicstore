package com.ihordev.repository;

import com.ihordev.domainprojections.SongAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface SongRepositoryCustom {

    Slice<SongAsPageItem> findSongsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                               Pageable pageRequest);

}
