package com.ihordev.repository;

import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

import static com.ihordev.repository.ArtistRepository.FIND_ALL_PAGINATED;


@NamedQueries({
    @NamedQuery(name = FIND_ALL_PAGINATED,
                query = " SELECT                                              " +
                        "       artist.id AS id,                              " +
                        "       artist.imageSmlUrl AS imageSmlUrl,            " +
                        "       localizedData.name AS name,                   " +
                        "       localizedData.description AS description      " +
                        " FROM Artist artist                                  " +
                        " JOIN artist.localizedDataSet localizedData          " +
                        " WHERE localizedData.language.name = :clientLanguage ")
})
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    String FIND_ALL_PAGINATED = "Artist.findAllPaginated";


    List<ArtistAsPageItem> findAllPaginated(@Param("clientLanguage") String clientLanguage);


}
