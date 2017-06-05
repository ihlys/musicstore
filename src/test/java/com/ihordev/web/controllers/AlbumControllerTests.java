package com.ihordev.web.controllers;

import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.SongService;
import com.ihordev.web.AbstractMockMvcTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.Collections;
import java.util.Locale;

import static com.ihordev.config.CustomWebConfig.DEFAULT_PAGE_REQUEST;
import static info.solidsoft.mockito.java8.AssertionMatcher.assertArg;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AlbumController.class)
public class AlbumControllerTests extends AbstractMockMvcTests {

    @MockBean
    private SongService songService;

    private final Slice<SongAsPageItem> songsOfAlbumWithId1 = new SliceImpl<>(Collections.emptyList());

    private void configureMockAlbumService(Pageable pageRequest) {
        given(songService.findSongsByAlbumIdProjectedPaginated(eq("en"), eq(1L), eq(pageRequest)))
                .willReturn(songsOfAlbumWithId1);
    }

    @Test
    public void shouldHandleAllRequestsForAlbumsWithCustomPrefixes() throws Exception {
        configureMockAlbumService(DEFAULT_PAGE_REQUEST);

        mockMvc.perform(get("/test/albums/1/songs")).andExpect(status().isOk());
        mockMvc.perform(get("/test/21/example-path/albums/1/songs")).andExpect(status().isOk());
        mockMvc.perform(get("/albums/1/songs")).andExpect(status().isOk());
    }

    @Test
    public void shouldHandleRequestsWithValidPathParamCorrectly() throws Exception {
        configureMockAlbumService(DEFAULT_PAGE_REQUEST);

        mockMvc.perform(get("/albums/1/songs"))
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
        configureMockAlbumService(DEFAULT_PAGE_REQUEST);

        mockMvc.perform(get("/albums/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"));
    }

    @Test
    public void shouldShowSongsOfConcreteAlbum() throws Exception  {
        configureMockAlbumService(DEFAULT_PAGE_REQUEST);

        mockMvc.perform(get("/albums/1/songs?page=0").locale(Locale.ENGLISH))
                .andExpect(status().isOk())
                .andExpect(view().name("songs"))
                .andExpect(model().attributeExists("songsPage"))
                .andExpect(model().attribute("songsPage", equalTo(songsOfAlbumWithId1)));

        then(songService).should(times(1)).findSongsByAlbumIdProjectedPaginated(
                assertArg(clientLanguage -> assertEquals(clientLanguage, "en")),
                assertArg(albumId -> assertEquals(albumId.longValue(), 1L)),
                assertArg(actualPageRequest -> assertEquals(DEFAULT_PAGE_REQUEST, actualPageRequest)));
    }
}
