package com.ihordev.repository;

import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface ArtistRepositoryCustom {

    Slice<ArtistAsPageItem> findArtistsByGenreIdProjectedPaginated(String clientLanguage, Long genreId,
                                                                   Pageable pageRequest);

}
