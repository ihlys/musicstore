package com.ihordev.web.controllers;

import com.ihordev.domainprojections.SongAsPageItem;
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

import static com.ihordev.web.controllers.AlbumController.PathVars.ALBUM_ID;
import static com.ihordev.web.controllers.AlbumController.PathVars.collectionToShowValues.SONGS;


@Controller
public class AlbumController {

    public static class PathVars {
        public static final String ALBUM_ID = "albumId";
        enum collectionToShowValues {SONGS}
    }

    @Autowired
    private SongService songService;

    @GetMapping(value = {"/**/albums/{"+ALBUM_ID+"}", "/**/albums/{"+ALBUM_ID+"}/{collectionToShow}"})
    public String albums(@PathVariable Long albumId,
                         @PathVariable(required = false) String collectionToShow,
                         Model model,
                         Locale locale,
                         Pageable pageRequest) {
        if (collectionToShow != null) {
            if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addAlbumsSongsToModel(model, locale.getLanguage(), albumId, pageRequest);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in album resource");
            }
        } else {
            addAlbumsSongsToModel(model, locale.getLanguage(), albumId, pageRequest);
            return "songs";
        }
    }

    private void addAlbumsSongsToModel(Model model, String clientLanguage, Long albumId, Pageable pageRequest) {
        Slice<SongAsPageItem> albumsSongs = songService.findSongsByAlbumIdProjectedPaginated(
                clientLanguage, albumId, pageRequest);
        model.addAttribute("songs", albumsSongs);
    }

}
