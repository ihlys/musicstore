package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.GenreAsPageItem;
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
public class GenreRepositoryTests {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DatabaseSetup("classpath:repository/data/genre-repository/" +
            "find-genre-subgenres-projected-paginated-source-data.xml")
    public void findArtistsByGenreIdProjectedPaginatedTest() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSliceA =
                genreRepository.findSubGenresByParentGenreIdProjectedPaginated("en", 2L, pageRequestA);

        List<GenreAsPageItem> contentA = actualGenresSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        GenreAsPageItem genreAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 3L, genreAsPageItemA1.getId());
        Assert.assertEquals("Genre Three", genreAsPageItemA1.getName());
        Assert.assertEquals("This is Genre Three", genreAsPageItemA1.getDescription());

        GenreAsPageItem genreAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 4L, genreAsPageItemA2.getId());
        Assert.assertEquals("Genre Four", genreAsPageItemA2.getName());
        Assert.assertEquals("This is Genre Four", genreAsPageItemA2.getDescription());

        GenreAsPageItem genreAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 5L, genreAsPageItemA3.getId());
        Assert.assertEquals("Genre Five", genreAsPageItemA3.getName());
        Assert.assertEquals("This is Genre Five", genreAsPageItemA3.getDescription());

        Assert.assertEquals(true, actualGenresSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSliceB =
                genreRepository.findSubGenresByParentGenreIdProjectedPaginated("en", 2L, pageRequestB);

        List<GenreAsPageItem> contentB = actualGenresSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        GenreAsPageItem genreAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 6L, genreAsPageItemB1.getId());
        Assert.assertEquals("Genre Six", genreAsPageItemB1.getName());
        Assert.assertEquals("This is Genre Six", genreAsPageItemB1.getDescription());

        Assert.assertEquals(false, actualGenresSliceB.hasNext());
    }
}
