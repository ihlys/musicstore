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

import javax.servlet.http.HttpServletRequest;

import static com.ihordev.core.util.UrlUtils.getFullUrl;
import static com.ihordev.core.util.UrlUtils.rewriteQueryParameter;
import static com.ihordev.web.controllers.ArtistController.PathVars.ARTIST_ID;
import static com.ihordev.web.controllers.ArtistController.PathVars.collectionToShowValues.ALBUMS;
import static com.ihordev.web.controllers.ArtistController.PathVars.collectionToShowValues.SONGS;


@Controller
public class ArtistController {

    public static class PathVars {
        public static final String ARTIST_ID = "artistId";
        enum collectionToShowValues {ALBUMS, SONGS}
    }

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;


    @GetMapping(value = {"/**/artists/{"+ARTIST_ID+"}", "/**/artists/{"+ARTIST_ID+"}/{collectionToShow}"})
    public String artist(@PathVariable Long artistId,
                         @PathVariable(required = false) String collectionToShow,
                         Model model,
                         HttpServletRequest request,
                         Pageable pageRequest) {
        if (collectionToShow != null) {
            if (ALBUMS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsAlbumsInfoToModel(model, request, artistId, pageRequest);
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addArtistsSongsInfoToModel(model, request, artistId, pageRequest);
            } else {
                throw new ResourceNotFoundException("There are no such items in artist resource");
            }
        } else {
            addArtistsAlbumsInfoToModel(model, request, artistId, pageRequest);
        }
        return "music-content";
    }

    private void addArtistsAlbumsInfoToModel(Model model, HttpServletRequest request, Long artistId, Pageable pageRequest) {
        Slice<AlbumAsPageItem> artistsAlbumsPage = albumService.findAlbumsByArtistIdProjectedPaginated(
                request.getLocale().getLanguage(), artistId, pageRequest);
        model.addAttribute("albumsPage", artistsAlbumsPage);
        model.addAttribute("musicEntitiesPageView", "albumsPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                String.valueOf(artistsAlbumsPage.getNumber() + 1)));
    }

    private void addArtistsSongsInfoToModel(Model model, HttpServletRequest request, Long artistsId, Pageable pageRequest) {
        Slice<SongAsPageItem> artistsSongsPage = songService.findSongsByArtistIdProjectedPaginated(
                request.getLocale().getLanguage(), artistsId, pageRequest);
        model.addAttribute("songsPage", artistsSongsPage);
        model.addAttribute("musicEntitiesPageView", "songsPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                        String.valueOf(artistsSongsPage.getNumber() + 1)));
    }

}
