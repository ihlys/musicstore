package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.service.AlbumService;
import com.ihordev.service.ArtistService;
import com.ihordev.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.web.PathVariables.*;


@Controller
public class ArtistController {

    @Autowired
    private Navigation navigation;

    @Autowired
    private PageableContentHelper pch;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;


    @GetMapping("/**/artists/{"+ ARTIST_ID+"}/{collectionToShow}")
    public String artist(@PathVariable Long artistId, @PathVariable String collectionToShow, Model model,
                         HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        String requestLang = request.getLocale().getLanguage();
        model.addAttribute("currentMusicEntity", artistService.findArtistAsCurrentMusicEntityById(
                artistId, requestLang));
        pch.set(model, request, response);
        if (ALBUMS.equals(collectionToShow)) {
            return pch.processRequest("albums", "albums", albumService.findAlbumsAsPageItemsByArtistId(artistId,
                    requestLang, pageRequest));
        } else if(SONGS.equals(collectionToShow)) {
            return pch.processRequest("songs", "songs", songService.findSongsAsPageItemsByArtistId(artistId,
                    requestLang, pageRequest));
        } else {
            throw new ResourceNotFoundException("There are no such items in artist resource");
        }
    }
}
