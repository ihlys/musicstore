package com.ihordev.web.controllers;

import com.ihordev.domainprojections.AlbumAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.AlbumService;
import com.ihordev.service.SongService;
import com.ihordev.web.AbstractMockMvcTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Collections;
import java.util.Locale;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ArtistController.class)
public class ArtistControllerTests extends AbstractMockMvcTests {

    @MockBean
    private AlbumService albumService;

    @MockBean
    private SongService songService;

    private final Pageable defaultPageRequest = new PageRequest(0, 20);
    private final Slice<AlbumAsPageItem> albumsOfArtistWithId1 = new SliceImpl<>(Collections.emptyList());
    private final Slice<SongAsPageItem> songsOfArtistWithId1 = new SliceImpl<>(Collections.emptyList());

    private void configureMockAlbumService(Pageable pageRequest) {
        given(albumService.findAlbumsByArtistIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(albumsOfArtistWithId1);
    }

    private void configureMockSongService(Pageable pageRequest) {
        given(songService.findSongsByArtistIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(songsOfArtistWithId1);
    }

    @Test
    public void shouldHandleAllRequestsForArtistsWithCustomPrefixes() throws Exception {
        configureMockAlbumService(defaultPageRequest);

        mockMvc.perform(get("/test/artists/1/albums").locale(Locale.ENGLISH)).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/artists/1/albums").locale(Locale.ENGLISH)).andExpect(status().isOk());
        mockMvc.perform(get("/artists/1/albums").locale(Locale.ENGLISH)).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        configureMockAlbumService(defaultPageRequest);

        mockMvc.perform(get("/~/artists/1/albums").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"));

        configureMockSongService(defaultPageRequest);

        mockMvc.perform(get("/~/artists/1/songs").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"));
    }

    @Test
    public void shouldNotHandleRequestsWithInvalidPathParam() throws Exception {
        mockMvc.perform(get("/~/artists/14/wrong-path"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldHandleRequestsWithAbsentPathParamAndReturnDefaultView() throws Exception {
        configureMockAlbumService(defaultPageRequest);

        mockMvc.perform(get("/~/artists/1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"));
    }

    @Test
    public void shouldShowAlbumsOfConcreteArtist() throws Exception {
        Pageable expectedPageRequest = new PageRequest(0, 1);
        configureMockAlbumService(expectedPageRequest);

        mockMvc.perform(get("/artists/1/albums?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"))
                .andExpect(model().attributeExists("albumsPage"))
                .andExpect(model().attribute("albumsPage", equalTo(albumsOfArtistWithId1)));

        then(albumService).should(times(1)).findAlbumsByArtistIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteArtist() throws Exception {
        Pageable expectedPageRequest = new PageRequest(0, 1);
        configureMockSongService(expectedPageRequest);

        mockMvc.perform(get("/artists/1/songs?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songsPage"))
                .andExpect(model().attribute("songsPage", equalTo(songsOfArtistWithId1)));

        then(songService).should(times(1)).findSongsByArtistIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

}
