package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.AlbumAsPageItem;
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
public class AlbumRepositoryTests {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DatabaseSetup("classpath:repository/data/album-repository/" +
            "find-albums-by-artist-id-projected-paginated-source-data.xml")
    public void findAlbumsByArtistProjectedPaginated() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<AlbumAsPageItem> actualAlbumsSliceA =
                albumRepository.findAlbumsByArtistIdProjectedPaginated("EN", 1L, pageRequestA);

        List<AlbumAsPageItem> contentA = actualAlbumsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        AlbumAsPageItem albumAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, albumAsPageItemA1.getId());

        AlbumAsPageItem albumAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, albumAsPageItemA2.getId());

        AlbumAsPageItem albumAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, albumAsPageItemA3.getId());

        Assert.assertEquals(true, actualAlbumsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<AlbumAsPageItem> actualAlbumsSliceB =
                albumRepository.findAlbumsByArtistIdProjectedPaginated("EN", 1L, pageRequestB);

        List<AlbumAsPageItem> contentB = actualAlbumsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        AlbumAsPageItem albumAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, albumAsPageItemB1.getId());

        Assert.assertEquals(false, actualAlbumsSliceB.hasNext());
    }
}
