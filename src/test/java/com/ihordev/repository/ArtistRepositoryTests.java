package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.ihordev.domain.Artist;
import com.ihordev.domain.ArtistLocalizedData;
import com.ihordev.domain.Genre;
import com.ihordev.domain.Language;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ihordev.config.AppProfiles.REPOSITORY_TESTS;


@RunWith(SpringRunner.class)
@ActiveProfiles(REPOSITORY_TESTS)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
public class ArtistRepositoryTests {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DatabaseSetup("classpath:repository/data/artist-repository/" +
            "find-artists-by-genre-id-projected-paginated-source-data.xml")
    public void findArtistsByGenreIdProjectedPaginatedTest() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSliceA =
                artistRepository.findArtistsByGenreIdProjectedPaginated("EN", 2L, pageRequestA);

        List<ArtistAsPageItem> contentA = actualArtistsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        ArtistAsPageItem artistAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, artistAsPageItemA1.getId());

        ArtistAsPageItem artistAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, artistAsPageItemA2.getId());

        ArtistAsPageItem artistAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, artistAsPageItemA3.getId());

        Assert.assertEquals(true, actualArtistsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSliceB =
                artistRepository.findArtistsByGenreIdProjectedPaginated("EN", 2L, pageRequestB);

        List<ArtistAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        ArtistAsPageItem artistAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, artistAsPageItemB1.getId());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }


    @Test
    @DatabaseSetup("classpath:repository/data/artist-repository/insert-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/artist-repository/insert-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void insertArtistTest() {
        Genre genre = em.find(Genre.class, 1L);
        Language language = em.find(Language.class, 1L);

        Artist artist = new Artist("Small image test url", "Large image test url", genre);

        Set<ArtistLocalizedData> localizedDataSet = new HashSet<>();
        ArtistLocalizedData artistLocalizedData = new ArtistLocalizedData("Test artist", "This is test artist",
                artist, language);
        localizedDataSet.add(artistLocalizedData);

        artist.setLocalizedDataSet(localizedDataSet);

        artistRepository.saveAndFlush(artist);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/artist-repository/update-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/artist-repository/update-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateArtistTest() {
        Genre anotherGenre = em.find(Genre.class, 2L);

        Artist artist = em.find(Artist.class, 1L);
        artist.setImageSmlUrl("Small image updated url");
        artist.setImageLgUrl("Large image updated url");
        artist.setGenre(anotherGenre);
        ArtistLocalizedData localizedData = artist.getLocalizedDataSet().iterator().next();
        localizedData.setName("Updated artist");
        localizedData.setDescription("This is updated artist");

        artistRepository.saveAndFlush(artist);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/artist-repository/remove-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/artist-repository/remove-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void removeArtistTest() {
        artistRepository.delete(1L);
        artistRepository.flush();
    }
}
