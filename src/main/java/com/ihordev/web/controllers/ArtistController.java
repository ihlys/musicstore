package com.ihordev.web.controllers;

import com.ihordev.domainprojections.AlbumAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.AlbumService;
import com.ihordev.service.SongService;
import com.ihordev.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Locale;

import static com.ihordev.web.controllers.ArtistController.PathVars.ARTISTS_ID;
import static com.ihordev.web.controllers.ArtistController.PathVars.collectionToShowValues.ALBUMS;
import static com.ihordev.web.controllers.ArtistController.PathVars.collectionToShowValues.SONGS;


@Controller
public class ArtistController {

    public static class PathVars {
        public static final String ARTISTS_ID = "artistsId";
        enum collectionToShowValues {ALBUMS, SONGS}
    }

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;


    @GetMapping(value = {"/**/artists/{"+ARTISTS_ID+"}", "/**/artists/{"+ARTISTS_ID+"}/{collectionToShow}"})
    public String artist(@PathVariable Long artistsId,
                         @PathVariable(required = false) String collectionToShow,
                         Model model,
                         Locale locale,
                         Pageable pageRequest) {
        if (collectionToShow != null) {
            if (ALBUMS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsAlbumsToModel(model, locale.getLanguage(), artistsId, pageRequest);
                return "albums";
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsSongsToModel(model, locale.getLanguage(), artistsId, pageRequest);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in artist resource");
            }
        } else {
            addArtistsAlbumsToModel(model, locale.getLanguage(), artistsId, pageRequest);
            return "albums";
        }
    }

    private void addArtistsAlbumsToModel(Model model, String clientLanguage, Long artistsId, Pageable pageRequest) {
        Slice<AlbumAsPageItem> artistsAlbums = albumService.findAlbumsByArtistIdProjectedPaginated(
                clientLanguage, artistsId, pageRequest);
        model.addAttribute("albums", artistsAlbums);
    }

    private void addArtistsSongsToModel(Model model, String clientLanguage, Long artistsId, Pageable pageRequest) {
        Slice<SongAsPageItem> artistsSongs = songService.findSongsByArtistIdProjectedPaginated(
                clientLanguage, artistsId, pageRequest);
        model.addAttribute("songs", artistsSongs);
    }

}
