package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.domainprojections.AlbumAsPageItem;
import com.ihordev.domainprojections.ArtistAsCurrentMusicEntity;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.AlbumService;
import com.ihordev.service.ArtistService;
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


@WebMvcTest(ArtistController.class)
public class ArtistControllerTests extends AbstractMockMvcTests {

    @MockBean
    private ArtistService artistService;

    @MockBean
    private AlbumService albumService;

    @MockBean
    private SongService songService;

    @MockBean
    private PageableContentHelper pchMock;

    @MockBean
    private Navigation navigationMock;

    private Slice<AlbumAsPageItem> albumsOfArtist1;
    private Slice<SongAsPageItem> songsOfArtist1;
    private ArtistAsCurrentMusicEntity artist1AsCurrentMusicEntity;
    private List<NavigationLink> navigationLinks;

    private final String RETURNED_VIEW_NAME = "returnedViewName";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.reset(artistService, albumService, songService, pchMock, navigationMock);

        albumsOfArtist1 = mock(Slice.class);
        songsOfArtist1 = mock(Slice.class);
        artist1AsCurrentMusicEntity = mock(ArtistAsCurrentMusicEntity.class);
        navigationLinks = mock(List.class);

        given(albumService.findAlbumsAsPageItemsByArtistId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(albumsOfArtist1);
        given(songService.findSongsAsPageItemsByArtistId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(songsOfArtist1);
        given(artistService.findArtistAsCurrentMusicEntityById(eq(1L), eq("en")))
                .willReturn(artist1AsCurrentMusicEntity);
    }

    @Test
    public void shouldShowAlbumsOfConcreteArtist() throws Exception {
        String requestURI = "/artists/1/albums";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("albums"), eq("albums"), eq(albumsOfArtist1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(artist1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "albums")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "albums")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, albumsOfArtist1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(artistService).should(times(1)).findArtistAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(albumService).should(times(1)).findAlbumsAsPageItemsByArtistId(
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteArtist() throws Exception {
        String requestURI = "/artists/1/songs";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("songs"), eq("songs"), eq(songsOfArtist1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(artist1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "songs")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "songs")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, songsOfArtist1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(artistService).should(times(1)).findArtistAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(songService).should(times(1)).findSongsAsPageItemsByArtistId(
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
