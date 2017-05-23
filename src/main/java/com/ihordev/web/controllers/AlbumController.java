package com.ihordev.web.controllers;

import com.ihordev.domain.Song;
import com.ihordev.service.SongService;
import com.ihordev.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.ihordev.web.controllers.AlbumController.PathVars.ALBUMS_ID;
import static com.ihordev.web.controllers.AlbumController.PathVars.collectionToShowValues.SONGS;


@Controller
public class AlbumController {

    public static class PathVars {
        public static final String ALBUMS_ID = "albumsId";
        enum collectionToShowValues {SONGS}
    }

    @Autowired
    private SongService songService;

    @RequestMapping(value = {"/**/albums/{"+ALBUMS_ID+"}", "/**/albums/{"+ALBUMS_ID+"}/{collectionToShow}"},
                    method = RequestMethod.GET)
    public String albums(@PathVariable long albumsId,
                         @PathVariable(required = false) String collectionToShow,
                         Model model) {
        if (collectionToShow != null) {
            if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addAlbumsSongsToModel(model, albumsId);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in album resource");
            }
        } else {
            addAlbumsSongsToModel(model, albumsId);
            return "songs";
        }
    }

    private void addAlbumsSongsToModel(Model model, long albumsId) {
        List<Song> albumsSongs = songService.findByAlbum(albumsId);
        model.addAttribute("songs", albumsSongs);
    }

}
