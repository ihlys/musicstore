package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
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
public class ThematicCompilationRepositoryTests {

    @Autowired
    private ThematicCompilationRepository thematicCompilationRepository;

    @Test
    @DatabaseSetup("classpath:data/repository/thematic-compilation-repository/" +
            "find-all-thematic-compilations-as-page-items-source-data.xml")
    public void findAllThematicCompilationsAsPageItemsTest_shouldFindPagesOfDataCorrectly() {
        Pageable pageRequestA = new PageRequest(0, 3, Sort.Direction.ASC, "id");

        Slice<ThematicCompilationAsPageItem> actualThematicCompilationsSliceA =
                thematicCompilationRepository.findAllThematicCompilationsAsPageItems("en", pageRequestA);

        List<ThematicCompilationAsPageItem> contentA = actualThematicCompilationsSliceA.getContent();
        Assert.assertEquals(3, contentA.size());

        ThematicCompilationAsPageItem thematicCompilationAsPageItemA1 = contentA.get(0);
        Assert.assertEquals((Long) 1L, thematicCompilationAsPageItemA1.getId());
        Assert.assertEquals("ThematicCompilation One", thematicCompilationAsPageItemA1.getName());
        Assert.assertEquals("ThematicCompilationOne-sm.jpg", thematicCompilationAsPageItemA1.getImageSmName());

        ThematicCompilationAsPageItem thematicCompilationAsPageItemA2 = contentA.get(1);
        Assert.assertEquals((Long) 2L, thematicCompilationAsPageItemA2.getId());
        Assert.assertEquals("ThematicCompilation Two", thematicCompilationAsPageItemA2.getName());
        Assert.assertEquals("ThematicCompilationTwo-sm.jpg", thematicCompilationAsPageItemA2.getImageSmName());

        ThematicCompilationAsPageItem thematicCompilationAsPageItemA3 = contentA.get(2);
        Assert.assertEquals((Long) 3L, thematicCompilationAsPageItemA3.getId());
        Assert.assertEquals("ThematicCompilation Three", thematicCompilationAsPageItemA3.getName());
        Assert.assertEquals("ThematicCompilationThree-sm.jpg", thematicCompilationAsPageItemA3.getImageSmName());

        Assert.assertEquals(true, actualThematicCompilationsSliceA.hasNext());

        Pageable pageRequestB = new PageRequest(1, 3, Sort.Direction.ASC, "id");

        Slice<ThematicCompilationAsPageItem> actualThematicCompilationsSliceB =
                thematicCompilationRepository.findAllThematicCompilationsAsPageItems("en", pageRequestB);

        List<ThematicCompilationAsPageItem> contentB = actualThematicCompilationsSliceB.getContent();
        Assert.assertEquals(1, contentB.size());

        ThematicCompilationAsPageItem thematicCompilationAsPageItemB1 = contentB.get(0);
        Assert.assertEquals((Long) 4L, thematicCompilationAsPageItemB1.getId());
        Assert.assertEquals("ThematicCompilation Four", thematicCompilationAsPageItemB1.getName());
        Assert.assertEquals("ThematicCompilationFour-sm.jpg", thematicCompilationAsPageItemB1.getImageSmName());

        Assert.assertEquals(false, actualThematicCompilationsSliceB.hasNext());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/thematic-compilation-repository/" +
            "find-all-thematic-compilations-as-page-items-source-data.xml")
    public void findAllThematicCompilationsAsPageItemsTest_shouldProvideDefaultL10nIfTargetIsAbsent() {
        Pageable pageRequest = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Slice<ThematicCompilationAsPageItem> actualThematicCompilationsSlice =
                thematicCompilationRepository.findAllThematicCompilationsAsPageItems("ru", pageRequest);

        List<ThematicCompilationAsPageItem> content = actualThematicCompilationsSlice.getContent();
        Assert.assertEquals(1, content.size());

        ThematicCompilationAsPageItem thematicCompilationAsPageItem = content.get(0);
        Assert.assertEquals((Long) 1L, thematicCompilationAsPageItem.getId());
        Assert.assertEquals("ThematicCompilation One", thematicCompilationAsPageItem.getName());
        Assert.assertEquals("ThematicCompilationOne-sm.jpg", thematicCompilationAsPageItem.getImageSmName());
    }

    @Test
    @DatabaseSetup("classpath:data/repository/thematic-compilation-repository/" +
            "find-thematic-compilation-as-current-music-entity-by-id-source-data.xml")
    public void findThematicCompilationAsCurrentMusicEntityByIdTest() {
        ThematicCompilationAsCurrentMusicEntity thematicCompilationAsCurrentMusicEntity =
                thematicCompilationRepository.findThematicCompilationAsCurrentMusicEntityById(1L, "en");

        Assert.assertEquals((Long) 1L, thematicCompilationAsCurrentMusicEntity.getId());
        Assert.assertEquals("ThematicCompilationOne-lg.jpg", thematicCompilationAsCurrentMusicEntity.getImageLgName());
        Assert.assertEquals("ThematicCompilation One", thematicCompilationAsCurrentMusicEntity.getName());
        Assert.assertEquals("This is ThematicCompilation One", thematicCompilationAsCurrentMusicEntity.getDescription());
    }
}
