package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.ihordev.domain.*;
import com.ihordev.domainprojections.SongAsPageItem;
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
import java.time.LocalDate;
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
public class SongRepositoryTests {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/" +
            "find-songs-by-genre-id-projected-paginated-source-data.xml")
    public void findSongsByGenreIdProjectedPaginatedTest() {
        Pageable pageRequestA = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsByGenreIdProjectedPaginated("EN", 2L, pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemA1.getId());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 5L, songAsPageItemA2.getId());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 6L, songAsPageItemA3.getId());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsByGenreIdProjectedPaginated("EN", 3L, pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(2, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());

        SongAsPageItem songAsPageItemB2 = contentB.get(1);
        Assert.assertEquals((Long) 5L, songAsPageItemB2.getId());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/" +
            "find-songs-by-artist-id-projected-paginated-source-data.xml")
    public void findSongsByArtistIdProjectedPaginatedTest() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsByArtistIdProjectedPaginated("EN", 1L, pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsByArtistIdProjectedPaginated("EN", 1L, pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/" +
            "find-songs-by-album-id-projected-paginated-source-data.xml")
    public void findSongsByAlbumIdProjectedPaginatedTest() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualSongsSliceA =
                songRepository.findSongsByAlbumIdProjectedPaginated("EN", 1L, pageRequestA);

        List<SongAsPageItem> contentA = actualSongsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SongAsPageItem songAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, songAsPageItemA1.getId());

        SongAsPageItem songAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, songAsPageItemA2.getId());

        SongAsPageItem songAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, songAsPageItemA3.getId());

        Assert.assertEquals(true, actualSongsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SongAsPageItem> actualArtistsSliceB =
                songRepository.findSongsByAlbumIdProjectedPaginated("EN", 1L, pageRequestB);

        List<SongAsPageItem> contentB = actualArtistsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SongAsPageItem songAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, songAsPageItemB1.getId());

        Assert.assertEquals(false, actualArtistsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/insert-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/song-repository/insert-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void insertSongTest() {
        Album album = em.find(Album.class, 1L);
        Language language = em.find(Language.class, 1L);

        Song song = new Song(album);

        Set<SongLocalizedData> localizedDataSet = new HashSet<>();
        SongLocalizedData songLocalizedData = new SongLocalizedData("Test song", song, language);
        localizedDataSet.add(songLocalizedData);

        song.setLocalizedDataSet(localizedDataSet);

        songRepository.saveAndFlush(song);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/update-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/song-repository/update-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateSongTest() {
        Album anotherAlbum = em.find(Album.class, 2L);

        Song song = em.find(Song.class, 1L);
        song.setAlbum(anotherAlbum);
        SongLocalizedData localizedData = song.getLocalizedDataSet().iterator().next();
        localizedData.setName("Updated song");

        songRepository.saveAndFlush(song);
    }

    @Test
    @DatabaseSetup("classpath:repository/data/song-repository/remove-source-data.xml")
    @ExpectedDatabase(value = "classpath:repository/data/song-repository/remove-expected-data.xml",
                      assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void removeSongTest() {
        songRepository.delete(1L);
        songRepository.flush();
    }
}
