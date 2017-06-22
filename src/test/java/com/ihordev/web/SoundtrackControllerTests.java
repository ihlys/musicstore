package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.navigation.NavigationLink;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.domainprojections.SoundtrackAsCurrentMusicEntity;
import com.ihordev.domainprojections.SoundtrackAsPageItem;
import com.ihordev.service.SongService;
import com.ihordev.service.SoundtrackService;
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

@WebMvcTest(SoundtrackController.class)
public class SoundtrackControllerTests extends AbstractMockMvcTests {

    @MockBean
    private SoundtrackService soundtrackService;

    @MockBean
    private SongService songService;

    @MockBean
    private PageableContentHelper pchMock;

    @MockBean
    private Navigation navigationMock;

    private Slice<SongAsPageItem> songsOfSoundtrack1;
    private Slice<SoundtrackAsPageItem> allSoundtracks;
    private SoundtrackAsCurrentMusicEntity soundtrack1AsCurrentMusicEntity;
    private List<NavigationLink> navigationLinks;

    private final String RETURNED_VIEW_NAME = "returnedViewName";

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.reset(soundtrackService, songService, pchMock);

        allSoundtracks = mock(Slice.class);
        songsOfSoundtrack1 = mock(Slice.class);
        soundtrack1AsCurrentMusicEntity = mock(SoundtrackAsCurrentMusicEntity.class);
        navigationLinks = mock(List.class);

        given(songService.findSongsAsPageItemsBySoundtrackId(eq(1L), eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(songsOfSoundtrack1);
        given(soundtrackService.findAllSoundtracksAsPageItems(eq("en"), eq(DEFAULT_PAGE_REQUEST)))
                .willReturn(allSoundtracks);
        given(soundtrackService.findSoundtrackAsCurrentMusicEntityById(eq(1L), eq("en")))
                .willReturn(soundtrack1AsCurrentMusicEntity);
    }

    @Test
    public void shouldShowAllSoundtracks() throws Exception  {
        String requestURI = "/soundtracks";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("soundtracks"), eq("soundtracks"), eq(allSoundtracks)))
                .willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "soundtracks")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "soundtracks")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, allSoundtracks)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(soundtrackService).should(times(1)).findAllSoundtracksAsPageItems(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteSoundtrack() throws Exception {
        String requestURI = "/soundtracks/1/songs";
        given(navigationMock.getNavigationLinks(argThat(requestWithURI(requestURI)))).willReturn(navigationLinks);
        given(pchMock.processRequest(eq("songs"), eq("songs"), eq(songsOfSoundtrack1))).willReturn(RETURNED_VIEW_NAME);

        mockMvc.perform(get(requestURI).locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name(RETURNED_VIEW_NAME))
                .andExpect(model().attributeExists("currentMusicEntity"))
                .andExpect(model().attribute("currentMusicEntity", equalTo(soundtrack1AsCurrentMusicEntity)));

        then(pchMock).should(times(1)).processRequest(
                assertArg(entitiesPageModelAttribute -> assertEquals(entitiesPageModelAttribute, "songs")),
                assertArg(entitiesPageMainView -> assertEquals(entitiesPageMainView, "songs")),
                assertArg(entitiesPage -> assertEquals(entitiesPage, songsOfSoundtrack1)));

        then(navigationMock).should(times(1)).getNavigationLinks(
                assertArg(request -> assertEquals(request.getRequestURI(), requestURI)));

        then(soundtrackService).should(times(1)).findSoundtrackAsCurrentMusicEntityById(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")));

        then(songService).should(times(1)).findSongsAsPageItemsBySoundtrackId(
                assertArg(soundtrackId -> assertEquals(soundtrackId.longValue(), 1L)),
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
