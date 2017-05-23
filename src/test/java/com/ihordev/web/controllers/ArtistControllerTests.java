package com.ihordev.web.controllers;

import com.ihordev.domain.Album;
import com.ihordev.domain.Song;
import com.ihordev.service.AlbumService;
import com.ihordev.service.SongService;
import com.ihordev.web.AbstractMockMvcTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static java.util.Arrays.asList;
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
        List<Album> albumsOfArtistWithId1 = asList(null);

        given(albumService.findByArtist(eq(1L))).willReturn(albumsOfArtistWithId1);

        mockMvc.perform(get("/artists/1/albums"))
                .andExpect(status().isOk())
                .andExpect(view().name("albums"))
                .andExpect(model().attributeExists("albums"))
                .andExpect(model().attribute("albums", equalTo(albumsOfArtistWithId1)));

        then(albumService).should(times(1)).findByArtist(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }

    @Test
    public void shouldShowSongsOfConcreteArtist() throws Exception  {
        List<Song> songsOfArtistWithId1 = asList(null);

        given(songService.findByArtist(eq(1L))).willReturn(songsOfArtistWithId1);

        mockMvc.perform(get("/artists/1/songs"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songs"))
                .andExpect(model().attribute("songs", equalTo(songsOfArtistWithId1)));

        then(songService).should(times(1)).findByArtist(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }

}
