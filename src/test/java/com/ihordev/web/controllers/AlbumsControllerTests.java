package com.ihordev.web.controllers;

import com.ihordev.domain.Song;
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


@WebMvcTest(AlbumsController.class)
public class AlbumsControllerTests extends AbstractMockMvcTests {

    @MockBean
    private SongService songService;

    @Test
    public void shouldHandleAllRequestsForAlbumsWithCustomPrefixes() throws Exception {
        mockMvc.perform(get("/test/albums/14/songs")).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/albums/14/songs")).andExpect(status().isOk());
        mockMvc.perform(get("/albums/14/songs")).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        mockMvc.perform(get("/albums/14/songs"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"));
    }

    @Test
    public void shouldNotHandleRequestsWithInvalidPathParam() throws Exception {
        mockMvc.perform(get("/albums/14/wrong-path"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldHandleRequestsWithAbsentPathParamAndReturnDefaultView() throws Exception {
        mockMvc.perform(get("/albums/14"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"));
    }

    @Test
    public void shouldShowSongsOfConcreteAlbum() throws Exception  {
        List<Song> songsOfAlbumWithId1 = null;

        given(songService.findByAlbum(eq(1L))).willReturn(songsOfAlbumWithId1);

        mockMvc.perform(get("/albums/1/songs"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songs"))
                .andExpect(model().attribute("songs", equalTo(songsOfAlbumWithId1)));

        then(songService).should(times(1)).findByAlbum(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }
}
