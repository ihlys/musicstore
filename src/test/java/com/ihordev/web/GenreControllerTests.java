package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.domainprojections.GenreAsCurrentMusicEntity;
import com.ihordev.domainprojections.GenreAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
import com.ihordev.service.SongService;
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


@WebMvcTest(GenreController.class)
public class GenreControllerTests extends AbstractMockMvcTests {

    @MockBean
    private GenreService genreService;

    @MockBean
    private ArtistService artistService;

    @MockBean
    private SongService songService;

    @MockBean
    private PageableContentHelper pchMock;

    @MockBean
    private Navigation navigationMock;

    private Slice<GenreAsPageItem> subGenresOfGenre1;
    private Slice<ArtistAsPageItem> artistsOfGenre1;
    private Slice<SongAsPageItem> songsOfGenre1;
    private GenreAsCurrentMusicEntity genre1AsCurrentMusicEntity;
    private List<NavigationLink> navigationLinks;

    private final String RETURNED_VIEW_NAME = "returnedViewName";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.reset(genreService, artistService, songService, pchMock, navigationMock);

        subGenresOfGenre1 = mock(Slice.class);
        artistsOfGenre1 = mock(Slice.class);
        songsOfGenre1 = mock(Slice.class);
        genre1AsCurrentMusicEntity = mock(GenreAsCurrentMusicEntity.class);
        navigationLinks = mock(List.class);

        given(artistService.findArtistsAsPageItemsByGenreId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(artistsOfGenre1);
        given(songService.findSongsAsPageItemsByGenreId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(songsOfGenre1);
        given(genreService.findGenresAsPageItemsByParentGenreId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(subGenresOfGenre1);
        given(genreService.findGenreAsCurrentMusicEntityById(eq(1L), eq("en")))
                .willReturn(genre1AsCurrentMusicEntity);
    }

    @Test
    public void shouldShowSubGenresOfConcreteGenre() throws Exception {
        String requestURI = "/genres/1/subgenres";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("genres"), eq("genres"), eq(subGenresOfGenre1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(genre1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "genres")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "genres")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, subGenresOfGenre1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(genreService).should(times(1)).findGenreAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(genreService).should(times(1)).findGenresAsPageItemsByParentGenreId(
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }

    @Test
    public void shouldShowArtistsOfConcreteGenre() throws Exception {
        String requestURI = "/genres/1/artists";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("artists"), eq("artists"), eq(artistsOfGenre1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(genre1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "artists")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "artists")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, artistsOfGenre1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(genreService).should(times(1)).findGenreAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(artistService).should(times(1)).findArtistsAsPageItemsByGenreId(
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteGenre() throws Exception {
        String requestURI = "/genres/1/songs";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("songs"), eq("songs"), eq(songsOfGenre1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(genre1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "songs")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "songs")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, songsOfGenre1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(genreService).should(times(1)).findGenreAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(songService).should(times(1)).findSongsAsPageItemsByGenreId(
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
