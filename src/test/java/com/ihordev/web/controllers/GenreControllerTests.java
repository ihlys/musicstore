package com.ihordev.web.controllers;

import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.domainprojections.GenreAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
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


@WebMvcTest(GenreController.class)
public class GenreControllerTests extends AbstractMockMvcTests {

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
        Slice<GenreAsPageItem> subGenresOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(genreService.findSubGenresByParentGenreIdProjectedPaginated(eq("en"), eq(null), eq(expectedPageRequest)))
                .willReturn(subGenresOfGenreWithId1);

        mockMvc.perform(get("/genres?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attribute("genres", equalTo(subGenresOfGenreWithId1)));

        then(genreService).should(times(1)).findSubGenresByParentGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id, null)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowSubGenresOfConcreteGenre() throws Exception {
        Slice<GenreAsPageItem> subGenresOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(genreService.findSubGenresByParentGenreIdProjectedPaginated(eq("en"), eq(1L), eq(expectedPageRequest)))
                .willReturn(subGenresOfGenreWithId1);

        mockMvc.perform(get("/genres/1?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(model().attribute("genres", equalTo(subGenresOfGenreWithId1)));

        then(genreService).should(times(1)).findSubGenresByParentGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowArtistsOfConcreteGenre() throws Exception {
        Slice<ArtistAsPageItem> artistsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(artistService.findArtistsByGenreIdProjectedPaginated(eq("en"), eq(1L), eq(expectedPageRequest)))
                .willReturn(artistsOfGenreWithId1);

        mockMvc.perform(get("/genres/1/artists?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("artists"))
                .andExpect(model().attributeExists("artists"))
                .andExpect(model().attribute("artists", equalTo(artistsOfGenreWithId1)));

        then(artistService).should(times(1)).findArtistsByGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteGenre() throws Exception {
        Slice<SongAsPageItem> songsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        Pageable expectedPageRequest = new PageRequest(0, 1);

        given(songService.findSongsByGenreIdProjectedPaginated(eq("en"), eq(1L), eq(expectedPageRequest)))
                .willReturn(songsOfGenreWithId1);

        mockMvc.perform(get("/genres/1/songs?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songs"))
                .andExpect(model().attribute("songs", equalTo(songsOfGenreWithId1)));

        then(songService).should(times(1)).findSongsByGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

}
