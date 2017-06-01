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

import javax.servlet.http.HttpServletRequest;

import static com.ihordev.core.util.UrlUtils.getFullUrl;
import static com.ihordev.core.util.UrlUtils.rewriteQueryParameter;
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
                         HttpServletRequest request,
                         Pageable pageRequest) {
        if (collectionToShow != null) {
            if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addAlbumsSongsInfoToModel(model, request, albumId, pageRequest);
            } else {
                throw new ResourceNotFoundException("There are no such items in album resource");
            }
        } else {
            addAlbumsSongsInfoToModel(model, request, albumId, pageRequest);
        }
        return "music-content";
    }

    private void addAlbumsSongsInfoToModel(Model model, HttpServletRequest request, Long artistsId, Pageable pageRequest) {
        Slice<SongAsPageItem> albumsSongsPage = songService.findSongsByAlbumIdProjectedPaginated(
                request.getLocale().getLanguage(), artistsId, pageRequest);
        model.addAttribute("songsPage", albumsSongsPage);
        model.addAttribute("musicEntitiesPageView", "songsPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                        String.valueOf(albumsSongsPage.getNumber() + 1)));
    }

}
