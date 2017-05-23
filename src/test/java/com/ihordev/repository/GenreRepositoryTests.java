package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.ihordev.domain.Genre;
import com.ihordev.domain.GenreLocalizedData;
import com.ihordev.domain.Language;
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
                genreRepository.findGenreSubGenresProjectedPaginated("EN", 2L, pageRequestA);

        List<GenreAsPageItem> contentA = actualGenresSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        GenreAsPageItem genreAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 3L, genreAsPageItemA1.getId());

        GenreAsPageItem genreAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 4L, genreAsPageItemA2.getId());

        GenreAsPageItem genreAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 5L, genreAsPageItemA3.getId());

        Assert.assertEquals(true, actualGenresSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSliceB =
                genreRepository.findGenreSubGenresProjectedPaginated("EN", 2L, pageRequestB);

        List<GenreAsPageItem> contentB = actualGenresSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        GenreAsPageItem genreAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 6L, genreAsPageItemB1.getId());

        Assert.assertEquals(false, actualGenresSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:repository/data/genre-repository/insert-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/genre-repository/insert-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void insertGenreTest() {
        Language language = em.find(Language.class, 1L);

        Genre genre = new Genre("Small image test url", "Large image test url", null);

        Set<GenreLocalizedData> localizedDataSet = new HashSet<>();
        GenreLocalizedData genreLocalizedData = new GenreLocalizedData("Test genre", "This is test genre",
                genre, language);
        localizedDataSet.add(genreLocalizedData);

        genre.setLocalizedDataSet(localizedDataSet);

        genreRepository.saveAndFlush(genre);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/genre-repository/update-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/genre-repository/update-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateGenreTest() {
        Genre anotherGenre = em.find(Genre.class, 1L);

        Genre genre = em.find(Genre.class, 2L);
        genre.setImageSmlUrl("Small image updated url");
        genre.setImageLgUrl("Large image updated url");
        genre.setParentGenre(anotherGenre);
        GenreLocalizedData localizedData = genre.getLocalizedDataSet().iterator().next();
        localizedData.setName("Updated genre");
        localizedData.setDescription("This is updated genre");

        genreRepository.saveAndFlush(genre);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/genre-repository/remove-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/genre-repository/remove-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void removeGenreTest() {
        genreRepository.delete(1L);
        genreRepository.flush();
    }
}
