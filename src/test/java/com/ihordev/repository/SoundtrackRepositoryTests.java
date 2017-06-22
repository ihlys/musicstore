package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
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
public class SoundtrackRepositoryTests {

    @Autowired
    private SoundtrackRepository soundtrackRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/soundtrack-repository/" +
            "find-all-soundtracks-as-page-items-source-data.xml")
    public void findAllSoundtracksAsPageItemsTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<SoundtrackAsPageItem> actualSoundtracksSliceA =
                soundtrackRepository.findAllSoundtracksAsPageItems("en", pageRequestA);

        List<SoundtrackAsPageItem> contentA = actualSoundtracksSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        SoundtrackAsPageItem soundtrackAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, soundtrackAsPageItemA1.getId());
        Assert.assertEquals("Soundtrack One", soundtrackAsPageItemA1.getName());
        Assert.assertEquals("SoundtrackOne-sm.jpg", soundtrackAsPageItemA1.getImageSmName());

        SoundtrackAsPageItem soundtrackAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, soundtrackAsPageItemA2.getId());
        Assert.assertEquals("Soundtrack Two", soundtrackAsPageItemA2.getName());
        Assert.assertEquals("SoundtrackTwo-sm.jpg", soundtrackAsPageItemA2.getImageSmName());

        SoundtrackAsPageItem soundtrackAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, soundtrackAsPageItemA3.getId());
        Assert.assertEquals("Soundtrack Three", soundtrackAsPageItemA3.getName());
        Assert.assertEquals("SoundtrackThree-sm.jpg", soundtrackAsPageItemA3.getImageSmName());

        Assert.assertEquals(true, actualSoundtracksSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<SoundtrackAsPageItem> actualSoundtracksSliceB =
                soundtrackRepository.findAllSoundtracksAsPageItems("en", pageRequestB);

        List<SoundtrackAsPageItem> contentB = actualSoundtracksSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        SoundtrackAsPageItem soundtrackAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, soundtrackAsPageItemB1.getId());
        Assert.assertEquals("Soundtrack Four", soundtrackAsPageItemB1.getName());
        Assert.assertEquals("SoundtrackFour-sm.jpg", soundtrackAsPageItemB1.getImageSmName());

        Assert.assertEquals(false, actualSoundtracksSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/soundtrack-repository/" +
            "find-all-soundtracks-as-page-items-source-data.xml")
    public void findAllSoundtracksAsPageItemsTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<SoundtrackAsPageItem> actualSoundtracksSlice =
                soundtrackRepository.findAllSoundtracksAsPageItems("ru", pageRequest);

        List<SoundtrackAsPageItem> content = actualSoundtracksSlice.getContent();
        Assert.assertEquals(1, content.size());

        SoundtrackAsPageItem soundtrackAsPageItem = content.get(0);
        Assert.assertEquals((Long) 1L, soundtrackAsPageItem.getId());
        Assert.assertEquals("Soundtrack One", soundtrackAsPageItem.getName());
        Assert.assertEquals("SoundtrackOne-sm.jpg", soundtrackAsPageItem.getImageSmName());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/soundtrack-repository/" +
            "find-soundtrack-as-current-music-entity-by-id-source-data.xml")
    public void findSoundtrackAsCurrentMusicEntityByIdTest() {
        SoundtrackAsCurrentMusicEntity soundtrackOneAsCurrentMusicEntity =
                soundtrackRepository.findSoundtrackAsCurrentMusicEntityById(1L, "en");

        Assert.assertEquals((Long) 1L, soundtrackOneAsCurrentMusicEntity.getId());
        Assert.assertEquals("SoundtrackOne-lg.jpg", soundtrackOneAsCurrentMusicEntity.getImageLgName());
        Assert.assertEquals("Soundtrack One", soundtrackOneAsCurrentMusicEntity.getName());
        Assert.assertEquals("This is Soundtrack One", soundtrackOneAsCurrentMusicEntity.getDescription());
    }
}
