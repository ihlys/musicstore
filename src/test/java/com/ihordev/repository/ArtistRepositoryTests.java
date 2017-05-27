package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import java.util.List;

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
                artistRepository.findArtistsByGenreIdProjectedPaginated("en", 2L, pageRequestA);

        List<ArtistAsPageItem> contentA = actualArtistsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        ArtistAsPageItem artistAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, artistAsPageItemA1.getId());
        Assert.assertEquals("Artist One", artistAsPageItemA1.getName());
        Assert.assertEquals("This is Artist One", artistAsPageItemA1.getDescription());

        ArtistAsPageItem artistAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, artistAsPageItemA2.getId());
        Assert.assertEquals("Artist Two", artistAsPageItemA2.getName());
        Assert.assertEquals("This is Artist Two", artistAsPageItemA2.getDescription());

        ArtistAsPageItem artistAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, artistAsPageItemA3.getId());
        Assert.assertEquals("Artist Three", artistAsPageItemA3.getName());
        Assert.assertEquals("This is Artist Three", artistAsPageItemA3.getDescription());

        Assert.assertEquals(true, actualArtistsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSliceB =
                artistRepository.findArtistsByGenreIdProjectedPaginated("en", 2L, pageRequestB);

        List<ArtistAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        ArtistAsPageItem artistAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, artistAsPageItemB1.getId());
        Assert.assertEquals("Artist Four", artistAsPageItemB1.getName());
        Assert.assertEquals("This is Artist Four", artistAsPageItemB1.getDescription());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }
}
