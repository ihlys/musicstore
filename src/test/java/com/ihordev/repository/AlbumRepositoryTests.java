package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.AlbumAsCurrentMusicEntity;
import com.ihordev.domainprojections.AlbumAsPageItem;
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
public class AlbumRepositoryTests {

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/album-repository/" +
            "find-albums-as-page-items-by-artist-id-source-data.xml")
    public void findAlbumsAsPageItemsByArtistIdTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<AlbumAsPageItem> actualAlbumsSliceA =
                albumRepository.findAlbumsAsPageItemsByArtistId(1L, "en", pageRequestA);

        List<AlbumAsPageItem> contentA = actualAlbumsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        AlbumAsPageItem albumAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, albumAsPageItemA1.getId());
        Assert.assertEquals("Album One", albumAsPageItemA1.getName());
        Assert.assertEquals("AlbumOne-sm.jpg", albumAsPageItemA1.getImageSmName());

        AlbumAsPageItem albumAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, albumAsPageItemA2.getId());
        Assert.assertEquals("Album Two", albumAsPageItemA2.getName());
        Assert.assertEquals("AlbumTwo-sm.jpg", albumAsPageItemA2.getImageSmName());

        AlbumAsPageItem albumAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, albumAsPageItemA3.getId());
        Assert.assertEquals("Album Three", albumAsPageItemA3.getName());
        Assert.assertEquals("AlbumThree-sm.jpg", albumAsPageItemA3.getImageSmName());

        Assert.assertEquals(true, actualAlbumsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<AlbumAsPageItem> actualAlbumsSliceB =
                albumRepository.findAlbumsAsPageItemsByArtistId(1L, "en", pageRequestB);

        List<AlbumAsPageItem> contentB = actualAlbumsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        AlbumAsPageItem albumAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, albumAsPageItemB1.getId());
        Assert.assertEquals("Album Four", albumAsPageItemB1.getName());
        Assert.assertEquals("AlbumFour-sm.jpg", albumAsPageItemB1.getImageSmName());

        Assert.assertEquals(false, actualAlbumsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/album-repository/" +
            "find-albums-as-page-items-by-artist-id-source-data.xml")
    public void findAlbumsAsPageItemsByArtistIdTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<AlbumAsPageItem> actualAlbumsSlice =
                albumRepository.findAlbumsAsPageItemsByArtistId(1L, "ru", pageRequest);

        List<AlbumAsPageItem> content = actualAlbumsSlice.getContent();
        Assert.assertEquals(1, content.size());

        AlbumAsPageItem albumAsPageItem = content.get(0);
        Assert.assertEquals((Long) 1L, albumAsPageItem.getId());
        Assert.assertEquals("Album One", albumAsPageItem.getName());
        Assert.assertEquals("AlbumOne-sm.jpg", albumAsPageItem.getImageSmName());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/album-repository/" +
            "find-album-as-current-music-entity-by-id-source-data.xml")
    public void findAlbumAsCurrentMusicEntityByIdTest() {
        AlbumAsCurrentMusicEntity albumOneAsCurrentMusicEntity =
                albumRepository.findAlbumAsCurrentMusicEntityById(1L, "en");

        Assert.assertEquals((Long) 1L, albumOneAsCurrentMusicEntity.getId());
        Assert.assertEquals("AlbumOne-lg.jpg", albumOneAsCurrentMusicEntity.getImageLgName());
        Assert.assertEquals("Album One", albumOneAsCurrentMusicEntity.getName());
        Assert.assertEquals("This is Album One", albumOneAsCurrentMusicEntity.getDescription());
    }
}
