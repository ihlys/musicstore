package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.ArtistAsPageItem;
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
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
        pattern = "com.ihordev.core.repositories.+"))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
public class ArtistRepositoryTests {

    @Autowired
    private ArtistRepository artistRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/artist-repository/" +
            "find-artists-as-page-items-by-genre-id-source-data.xml")
    public void findArtistsAsPageItemsByGenreId_shouldFindPagesOfDataCorrectly() {
        // test first page
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSliceA =
                artistRepository.findArtistsAsPageItemsByGenreId(2L, "en", pageRequestA);

        List<ArtistAsPageItem> contentA = actualArtistsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        ArtistAsPageItem artistAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, artistAsPageItemA1.getId());
        Assert.assertEquals("Artist One", artistAsPageItemA1.getName());
        Assert.assertEquals("ArtistOne-sm.jpg", artistAsPageItemA1.getImageSmName());

        ArtistAsPageItem artistAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, artistAsPageItemA2.getId());
        Assert.assertEquals("Artist Two", artistAsPageItemA2.getName());
        Assert.assertEquals("ArtistTwo-sm.jpg", artistAsPageItemA2.getImageSmName());

        ArtistAsPageItem artistAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, artistAsPageItemA3.getId());
        Assert.assertEquals("Artist Three", artistAsPageItemA3.getName());
        Assert.assertEquals("ArtistThree-sm.jpg", artistAsPageItemA3.getImageSmName());

        Assert.assertEquals(true, actualArtistsSliceA.hasNext());

        // test second page
        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSliceB =
                artistRepository.findArtistsAsPageItemsByGenreId(2L, "en", pageRequestB);

        List<ArtistAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        ArtistAsPageItem artistAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, artistAsPageItemB1.getId());
        Assert.assertEquals("Artist Four", artistAsPageItemB1.getName());
        Assert.assertEquals("ArtistFour-sm.jpg", artistAsPageItemB1.getImageSmName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/artist-repository/" +
            "find-artists-as-page-items-by-genre-id-source-data.xml")
    public void findArtistsAsPageItemsByGenreIdTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<ArtistAsPageItem> actualArtistsSlice =
                artistRepository.findArtistsAsPageItemsByGenreId(2L, "ru", pageRequest);

        List<ArtistAsPageItem> content = actualArtistsSlice.getContent();
        Assert.assertEquals(1, content.size());

        ArtistAsPageItem artistAsPageItem = content.get(0);
        Assert.assertEquals((Long) 1L, artistAsPageItem.getId());
        Assert.assertEquals("Artist One", artistAsPageItem.getName());
        Assert.assertEquals("ArtistOne-sm.jpg", artistAsPageItem.getImageSmName());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/artist-repository/" +
            "find-artist-as-current-music-entity-by-id-source-data.xml")
    public void findArtistAsCurrentMusicEntityByIdTest() {
        ArtistAsCurrentMusicEntity artistOneAsCurrentMusicEntity =
                artistRepository.findArtistAsCurrentMusicEntityById(1L, "en");

        Assert.assertEquals((Long) 1L, artistOneAsCurrentMusicEntity.getId());
        Assert.assertEquals("ArtistOne-lg.jpg", artistOneAsCurrentMusicEntity.getImageLgName());
        Assert.assertEquals("Artist One", artistOneAsCurrentMusicEntity.getName());
        Assert.assertEquals("This is Artist One", artistOneAsCurrentMusicEntity.getDescription());
    }
}
