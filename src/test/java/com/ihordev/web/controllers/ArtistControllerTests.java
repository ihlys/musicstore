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

    @Test
    public void shouldHandleAllRequestsForArtistsWithCustomPrefixes() throws Exception {
        mockMvc.perform(get("/test/artists/14/albums")).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/artists/14/albums")).andExpect(status().isOk());
        mockMvc.perform(get("/artists/14/albums")).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        mockMvc.perform(get("/~/artists/14/albums"))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"));

        mockMvc.perform(get("/~/artists/14/songs"))
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
        mockMvc.perform(get("/~/artists/14"))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"));
    }

    @Test
    public void shouldShowAlbumsOfConcreteArtist() throws Exception  {
        Slice<AlbumAsPageItem> albumsOfArtistWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(albumService.findAlbumsByArtistIdProjectedPaginated(eq("en"), eq(1L), eq(expectedPageRequest)))
                .willReturn(albumsOfArtistWithId1);

        mockMvc.perform(get("/artists/1/albums?page=1&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"))
                .andExpect(model().attributeExists("albums"))
                .andExpect(model().attribute("albums", equalTo(albumsOfArtistWithId1)));

        then(albumService).should(times(1)).findAlbumsByArtistIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteArtist() throws Exception  {
        Slice<SongAsPageItem> songsOfArtistWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(songService.findSongsByArtistIdProjectedPaginated(eq("en"), eq(1L), eq(expectedPageRequest)))
                .willReturn(songsOfArtistWithId1);

        mockMvc.perform(get("/artists/1/songs?page=1&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songs"))
                .andExpect(model().attribute("songs", equalTo(songsOfArtistWithId1)));

        then(songService).should(times(1)).findSongsByArtistIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

}
