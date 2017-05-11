package com.ihordev.web.controllers;

import com.ihordev.domain.Artist;
import com.ihordev.domain.Genre;
import com.ihordev.domain.Song;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
import com.ihordev.service.SongService;
import com.ihordev.web.AbstractMockMvcTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(GenresController.class)
public class GenresControllerTests extends AbstractMockMvcTests {

    @MockBean
    private GenreService genreService;

    @MockBean
    private ArtistService artistService;

    @MockBean
    private SongService songService;

    @Test
    public void shouldHandleAllRequestsForGenresWithAnyPrefixes() throws Exception {
        mockMvc.perform(get("/test/genres/14/artists")).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/genres/14/artists")).andExpect(status().isOk());
        mockMvc.perform(get("/genres/14/artists")).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        mockMvc.perform(get("/genres/14/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"));
        mockMvc.perform(get("/genres/14/artists"))
                .andExpect(status().isOk())
                .andExpect(view().name("artists"));
        mockMvc.perform(get("/genres/14/songs"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"));
    }

    @Test
    public void shouldNotHandleRequestWithInvalidPathParam() throws Exception {
        mockMvc.perform(get("/genres/14/wrong-path"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldHandleRequestWithAbsentPathParamAndReturnDefaultView() throws Exception {
        mockMvc.perform(get("/genres/14"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"));
    }

    @Test
    public void shouldShowAllGenres() throws Exception {
        List<Genre> allGenres = Collections.emptyList();

        given(genreService.findAllGenres()).willReturn(allGenres);

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attribute("genres", equalTo(allGenres)));

        then(genreService).should(times(1)).findAllGenres();
    }

    @Test
    public void shouldShowSubGenresOfConcreteGenre() throws Exception  {
        List<Genre> subGenresOfGenreWithId1 = asList(new Genre());

        given(genreService.findSubgenres(eq(1L))).willReturn(subGenresOfGenreWithId1);

        mockMvc.perform(get("/genres/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attribute("genres", equalTo(subGenresOfGenreWithId1)));

        then(genreService).should(times(1)).findSubgenres(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }

    @Test
    public void shouldShowArtistsOfConcreteGenre() throws Exception  {
        List<Artist> artistsOfGenreWithId1 = asList(new Artist());

        given(artistService.findByGenre(eq(1L))).willReturn(artistsOfGenreWithId1);

        mockMvc.perform(get("/genres/1/artists"))
                .andExpect(status().isOk())
                .andExpect(view().name("artists"))
                .andExpect(model().attributeExists("artists"))
                .andExpect(model().attribute("artists", equalTo(artistsOfGenreWithId1)));

        then(artistService).should(times(1)).findByGenre(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }

    @Test
    public void shouldShowSongsOfConcreteGenre() throws Exception  {
        List<Song> songsOfGenreWithId1 = asList(new Song());

        given(songService.findByGenre(eq(1L))).willReturn(songsOfGenreWithId1);

        mockMvc.perform(get("/genres/1/songs"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songs"))
                .andExpect(model().attribute("songs", equalTo(songsOfGenreWithId1)));

        then(songService).should(times(1)).findByGenre(assertArg(id -> assertEquals(id.longValue(), 1L)));
    }

}
