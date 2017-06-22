package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.domainprojections.ThematicCompilationAsCurrentMusicEntity;
import com.ihordev.domainprojections.ThematicCompilationAsPageItem;
import com.ihordev.service.SongService;
import com.ihordev.service.ThematicCompilationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Locale;

import static com.ihordev.config.CustomWebConfig.DEFAULT_PAGE_REQUEST;
import static com.ihordev.testutils.MatcherUtils.requestWithURI;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThematicCompilationController.class)
public class ThematicCompilationControllerTests extends AbstractMockMvcTests {

    @MockBean
    private ThematicCompilationService thematicCompilationService;

    @MockBean
    private SongService songService;

    @MockBean
    private PageableContentHelper pchMock;

    @MockBean
    private Navigation navigationMock;

    private Slice<SongAsPageItem> songsOfThematicCompilation1;
    private Slice<ThematicCompilationAsPageItem> allThematicCompilations;
    private ThematicCompilationAsCurrentMusicEntity thematicCompilation1AsCurrentMusicEntity;
    private List<NavigationLink> navigationLinks;

    private final static String RETURNED_VIEW_NAME = "returnedViewName";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.reset(thematicCompilationService, songService, pchMock);

        songsOfThematicCompilation1 = mock(Slice.class);
        allThematicCompilations = mock(Slice.class);
        thematicCompilation1AsCurrentMusicEntity = mock(ThematicCompilationAsCurrentMusicEntity.class);
        navigationLinks = mock(List.class);

        given(thematicCompilationService.findAllThematicCompilationsAsPageItems(eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(allThematicCompilations);
        given(thematicCompilationService.findThematicCompilationAsCurrentMusicEntityById(eq(1L), eq("en")))
                .willReturn(thematicCompilation1AsCurrentMusicEntity);
        given(songService.findSongsAsPageItemsByThematicCompilationId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(songsOfThematicCompilation1);
    }

    @Test
    public void shouldShowAllThematicCompilations() throws Exception {
        String requestURI = "/thematic-compilations";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("thematicCompilations"), eq("thematic-compilations"),
                eq(allThematicCompilations))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "thematicCompilations")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "thematic-compilations")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, allThematicCompilations)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(thematicCompilationService).should(times(1)).findAllThematicCompilationsAsPageItems(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteThematicCompilation() throws Exception {
        String requestURI = "/thematic-compilations/1/songs";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("songs"), eq("songs"), eq(songsOfThematicCompilation1)))
                .willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(thematicCompilation1AsCurrentMusicEntity)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "songs")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "songs")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, songsOfThematicCompilation1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(thematicCompilationService).should(times(1)).findThematicCompilationAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(songService).should(times(1)).findSongsAsPageItemsByThematicCompilationId(
                assertArg(thematicCompilationId -> assertEquals(thematicCompilationId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
