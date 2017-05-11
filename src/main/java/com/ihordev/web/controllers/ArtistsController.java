package com.ihordev.web.controllers;

import com.ihordev.domain.Album;
import com.ihordev.domain.Song;
import com.ihordev.service.AlbumService;
import com.ihordev.service.SongService;
import com.ihordev.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.ihordev.web.controllers.ArtistsController.PathVars.ARTISTS_ID;
import static com.ihordev.web.controllers.ArtistsController.PathVars.collectionToShowValues.ALBUMS;
import static com.ihordev.web.controllers.ArtistsController.PathVars.collectionToShowValues.SONGS;


@Controller
public class ArtistsController {

    public static class PathVars {
        public static final String ARTISTS_ID = "artistsId";
        enum collectionToShowValues {ALBUMS, SONGS}
    }

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;


    @RequestMapping(value = {"/**/artists/{"+ARTISTS_ID+"}", "/**/artists/{"+ARTISTS_ID+"}/{collectionToShow}"},
                    method = RequestMethod.GET)
    public String artist(@PathVariable long artistsId,
                         @PathVariable(required = false) String collectionToShow,
                         Model model) {
        if (collectionToShow != null) {
            if (ALBUMS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsAlbumsToModel(model, artistsId);
                return "albums";
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsSongsToModel(model, artistsId);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in artist resource");
            }
        } else {
            addArtistsAlbumsToModel(model, artistsId);
            return "albums";
        }
    }

    private void addArtistsAlbumsToModel(Model model, long artistsId) {
        List<Album> artistsAlbums = albumService.findByArtist(artistsId);
        model.addAttribute("albums", artistsAlbums);
    }

    private void addArtistsSongsToModel(Model model, long artistsId) {
        List<Song> artistsSongs = songService.findByArtist(artistsId);
        model.addAttribute("songs", artistsSongs);
    }

}
