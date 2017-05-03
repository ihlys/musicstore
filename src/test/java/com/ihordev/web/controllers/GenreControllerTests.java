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

    private final Pageable defaultPageRequest = new PageRequest(0, 20);
    private final Slice<GenreAsPageItem> subGenresOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
    private final Slice<ArtistAsPageItem> artistsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
    private final Slice<SongAsPageItem> songsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());

    private void configureMockGenreService(Pageable pageRequest) {
        given(genreService.findSubGenresByParentGenreIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(subGenresOfGenreWithId1);
    }

    private void configureMockArtistService(Pageable pageRequest) {
        Slice<ArtistAsPageItem> artistsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        given(artistService.findArtistsByGenreIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(artistsOfGenreWithId1);
    }

    private void configureMockSongService(Pageable pageRequest) {
        Slice<SongAsPageItem> songsOfGenreWithId1 = new SliceImpl<>(Collections.emptyList());
        given(songService.findSongsByGenreIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(songsOfGenreWithId1);
    }

    @Test
    public void shouldHandleAllRequestsForGenresWithAnyPrefixes() throws Exception {
        configureMockArtistService(defaultPageRequest);

        mockMvc.perform(get("/test/genres/1/artists").locale(Locale.ENGLISH)).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/genres/1/artists").locale(Locale.ENGLISH)).andExpect(status().isOk());
        mockMvc.perform(get("/genres/1/artists").locale(Locale.ENGLISH)).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        configureMockGenreService(defaultPageRequest);

        mockMvc.perform(get("/genres/1/subgenres").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"));

        configureMockArtistService(defaultPageRequest);

        mockMvc.perform(get("/genres/1/artists").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("artists"));

        configureMockSongService(defaultPageRequest);

        mockMvc.perform(get("/genres/1/songs").locale(Locale.ENGLISH))
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
        configureMockGenreService(defaultPageRequest);

        mockMvc.perform(get("/genres/1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"));
    }

    @Test
    public void shouldShowSubGenresOfConcreteGenre() throws Exception {
        Pageable expectedPageRequest = new PageRequest(0, 1);
        configureMockGenreService(expectedPageRequest);

        mockMvc.perform(get("/genres/1?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("genres"))
                .andExpect(model().attributeExists("genresPage"))
                .andExpect(model().attribute("genresPage", equalTo(subGenresOfGenreWithId1)));

        then(genreService).should(times(1)).findSubGenresByParentGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowArtistsOfConcreteGenre() throws Exception {
        Pageable expectedPageRequest = new PageRequest(0, 1);
        configureMockArtistService(expectedPageRequest);

        mockMvc.perform(get("/genres/1/artists?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("artists"))
                .andExpect(model().attributeExists("artistsPage"))
                .andExpect(model().attribute("artistsPage", equalTo(artistsOfGenreWithId1)));

        then(artistService).should(times(1)).findArtistsByGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

    @Test
    public void shouldShowSongsOfConcreteGenre() throws Exception {
        Pageable expectedPageRequest = new PageRequest(0, 1);
        configureMockSongService(expectedPageRequest);

        mockMvc.perform(get("/genres/1/songs?page=0&size=1").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songsPage"))
                .andExpect(model().attribute("songsPage", equalTo(songsOfGenreWithId1)));

        then(songService).should(times(1)).findSongsByGenreIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(id -> assertEquals(id.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(expectedPageRequest, actualPageRequest)));
    }

}
