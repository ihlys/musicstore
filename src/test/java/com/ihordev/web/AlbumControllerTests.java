package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.domainprojections.AlbumAsCurrentMusicEntity;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.AlbumService;
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


@WebMvcTest(AlbumController.class)
public class AlbumControllerTests extends AbstractMockMvcTests {

    @MockBean
    private AlbumService albumService;

    @MockBean
    private SongService songService;

    @MockBean
    private PageableContentHelper pchMock;

    @MockBean
    private Navigation navigationMock;

    private Slice<SongAsPageItem> songsOfAlbum1;
    private AlbumAsCurrentMusicEntity album1AsCurrentMusicEntity;
    private List<NavigationLink> navigationLinks;

    private final String RETURNED_VIEW_NAME = "returnedViewName";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.reset(albumService, songService, pchMock, navigationMock);

        songsOfAlbum1 = mock(Slice.class);
        album1AsCurrentMusicEntity = mock(AlbumAsCurrentMusicEntity.class);
        navigationLinks = mock(List.class);

        given(songService.findSongsAsPageItemsByAlbumId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(songsOfAlbum1);
        given(albumService.findAlbumAsCurrentMusicEntityById(eq(1L), eq("en")))
                .willReturn(album1AsCurrentMusicEntity);
    }

    @Test
    public void shouldShowSongsOfConcreteAlbum() throws Exception {
        String requestURI = "/albums/1/songs";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("songs"), eq("songs"), eq(songsOfAlbum1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(album1AsCurrentMusicEntity)))
                .andExpect(model().attribute("navigation", equalTo(navigationLinks)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "songs")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "songs")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, songsOfAlbum1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(albumService).should(times(1)).findAlbumAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(songService).should(times(1)).findSongsAsPageItemsByAlbumId(
                assertArg(albumId -> assertEquals(albumId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
