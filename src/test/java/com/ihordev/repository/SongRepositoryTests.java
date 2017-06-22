package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
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
public class SongRepositoryTests {

    @Autowired
    private SongRepository songRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-genre-id-source-data.xml")
    public void findSongsAsPageItemsByGenreIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsAsPageItemsByGenreId(2L, "en", pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemA1.getId());
        Assert.assertEquals("Song Four", songAsPageItemA1.getName());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 5L, songAsPageItemA2.getId());
        Assert.assertEquals("Song Five", songAsPageItemA2.getName());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 6L, songAsPageItemA3.getId());
        Assert.assertEquals("Song Six", songAsPageItemA3.getName());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsAsPageItemsByGenreId(3L, "en", pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(2, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());
        Assert.assertEquals("Song Four", songAsPageItemB1.getName());

        SongAsPageItem songAsPageItemB2 = contentB.get(1);
        Assert.assertEquals((Long) 5L, songAsPageItemB2.getId());
        Assert.assertEquals("Song Five", songAsPageItemB2.getName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-artist-id-source-data.xml")
    public void findSongsAsPageItemsByArtistIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsAsPageItemsByArtistId(1L, "en", pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());
        Assert.assertEquals("Song One", songAsPageItemA1.getName());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());
        Assert.assertEquals("Song Two", songAsPageItemA2.getName());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());
        Assert.assertEquals("Song Three", songAsPageItemA3.getName());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsAsPageItemsByArtistId(1L, "en", pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());
        Assert.assertEquals("Song Four", songAsPageItemB1.getName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-album-id-source-data.xml")
    public void findSongsAsPageItemsByAlbumIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsAsPageItemsByAlbumId(1L, "en", pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());
        Assert.assertEquals("Song One", songAsPageItemA1.getName());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());
        Assert.assertEquals("Song Two", songAsPageItemA2.getName());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());
        Assert.assertEquals("Song Three", songAsPageItemA3.getName());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsAsPageItemsByAlbumId(1L, "en", pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());
        Assert.assertEquals("Song Four", songAsPageItemB1.getName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-soundtrack-id-source-data.xml")
    public void findSongsAsPageItemsBySoundtrackIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsAsPageItemsBySoundtrackId(1L, "en", pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());
        Assert.assertEquals("Song One", songAsPageItemA1.getName());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());
        Assert.assertEquals("Song Two", songAsPageItemA2.getName());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());
        Assert.assertEquals("Song Three", songAsPageItemA3.getName());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsAsPageItemsBySoundtrackId(1L, "en", pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());
        Assert.assertEquals("Song Four", songAsPageItemB1.getName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-thematic-compilation-id-source-data.xml")
    public void findSongsAsPageItemsByThematicCompilationIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsAsPageItemsByThematicCompilationId(1L, "en", pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());
        Assert.assertEquals("Song One", songAsPageItemA1.getName());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());
        Assert.assertEquals("Song Two", songAsPageItemA2.getName());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());
        Assert.assertEquals("Song Three", songAsPageItemA3.getName());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsAsPageItemsByThematicCompilationId(1L, "en", pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());
        Assert.assertEquals("Song Four", songAsPageItemB1.getName());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/song-repository/" +
            "find-songs-as-page-items-by-genre-id-source-data.xml")
    public void findSongsAsPageItemsByGenreIdTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSlice =
                songRepository.findSongsAsPageItemsByGenreId(2L, "ru", pageRequest);

        List<SongAsPageItem> content = actualSongsSlice.getContent();
        Assert.assertEquals(1, content.size());

        SongAsPageItem songAsPageItem = content.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItem.getId());
        Assert.assertEquals("Song One", songAsPageItem.getName());
    }
}
