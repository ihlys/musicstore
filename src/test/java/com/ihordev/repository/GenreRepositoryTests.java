package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import java.util.List;

import static com.ihordev.config.AppProfiles.REPOSITORY_TESTS;


@RunWith(SpringRunner.class)
@ActiveProfiles(REPOSITORY_TESTS)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.ihordev.core.repositories.+"))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
public class GenreRepositoryTests {

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/genre-repository/" +
            "find-genres-as-page-items-by-parent-genre-id-source-data.xml")
    public void findGenresAsPageItemsByParentGenreIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSliceA =
                genreRepository.findGenresAsPageItemsByParentGenreId(2L, "en", pageRequestA);

        List<GenreAsPageItem> contentA = actualGenresSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        GenreAsPageItem genreAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 3L, genreAsPageItemA1.getId());
        Assert.assertEquals("Genre Three", genreAsPageItemA1.getName());
        Assert.assertEquals("GenreThree-sm.jpg", genreAsPageItemA1.getImageSmName());

        GenreAsPageItem genreAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 4L, genreAsPageItemA2.getId());
        Assert.assertEquals("Genre Four", genreAsPageItemA2.getName());
        Assert.assertEquals("GenreFour-sm.jpg", genreAsPageItemA2.getImageSmName());

        GenreAsPageItem genreAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 5L, genreAsPageItemA3.getId());
        Assert.assertEquals("Genre Five", genreAsPageItemA3.getName());
        Assert.assertEquals("GenreFive-sm.jpg", genreAsPageItemA3.getImageSmName());

        Assert.assertEquals(true, actualGenresSliceA.hasNext());

        // ------------

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSliceB =
                genreRepository.findGenresAsPageItemsByParentGenreId(2L, "en", pageRequestB);

        List<GenreAsPageItem> contentB = actualGenresSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        GenreAsPageItem genreAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 6L, genreAsPageItemB1.getId());
        Assert.assertEquals("Genre Six", genreAsPageItemB1.getName());
        Assert.assertEquals("GenreSix-sm.jpg", genreAsPageItemB1.getImageSmName());

        // ------------

        Slice<GenreAsPageItem> actualGenresSliceC =
                genreRepository.findGenresAsPageItemsByParentGenreId(null, "en", pageRequestA);

        List<GenreAsPageItem> contentC = actualGenresSliceC.getContent();
        Assert.assertEquals(1, contentC.size());

        GenreAsPageItem genreAsPageItemC1 = contentC.get(0);
        Assert.assertEquals((Long) 1L, genreAsPageItemC1.getId());
        Assert.assertEquals("Genre One", genreAsPageItemC1.getName());
        Assert.assertEquals("GenreOne-sm.jpg", genreAsPageItemC1.getImageSmName());

        Assert.assertEquals(false, actualGenresSliceC.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/genre-repository/" +
            "find-genres-as-page-items-by-parent-genre-id-source-data.xml")
    public void findGenresAsPageItemsByParentGenreIdTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<GenreAsPageItem> actualGenresSlice =
                genreRepository.findGenresAsPageItemsByParentGenreId(1L, "ru", pageRequest);

        List<GenreAsPageItem> content = actualGenresSlice.getContent();
        Assert.assertEquals(1, content.size());

        GenreAsPageItem genreAsPageItem = content.get(0);
        Assert.assertEquals((Long) 2L, genreAsPageItem.getId());
        Assert.assertEquals("Genre Two", genreAsPageItem.getName());
        Assert.assertEquals("GenreTwo-sm.jpg", genreAsPageItem.getImageSmName());
    }


    @Test
    @DatabaseSetup("classpath:data/repository/genre-repository/" +
            "find-genre-as-current-music-entity-by-id-source-data.xml")
    public void findGenreAsCurrentMusicEntityByIdTest() {
        GenreAsCurrentMusicEntity genreOneAsCurrentMusicEntity =
                genreRepository.findGenreAsCurrentMusicEntityById(1L, "en");

        Assert.assertEquals((Long) 1L, genreOneAsCurrentMusicEntity.getId());
        Assert.assertEquals("GenreOne-lg.jpg", genreOneAsCurrentMusicEntity.getImageLgName());
        Assert.assertEquals("Genre One", genreOneAsCurrentMusicEntity.getName());
        Assert.assertEquals("This is Genre One", genreOneAsCurrentMusicEntity.getDescription());
    }
}
