package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.service.AlbumService;
import com.ihordev.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.web.PathVariables.ALBUM_ID;
import static com.ihordev.web.PathVariables.SONGS;


@Controller
public class AlbumController {

    @Autowired
    private Navigation navigation;

    @Autowired
    private PageableContentHelper pch;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;

    @GetMapping("/**/albums/{"+ ALBUM_ID+"}/{collectionToShow}")
    public String albums(@PathVariable Long albumId, @PathVariable String collectionToShow, Model model,
                         HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        String requestLang = request.getLocale().getLanguage();
        model.addAttribute("currentMusicEntity", albumService.findAlbumAsCurrentMusicEntityById(albumId, requestLang));
        pch.set(model, request, response);
        if (SONGS.equals(collectionToShow)) {
            return pch.processRequest("songs", "songs", songService.findSongsAsPageItemsByAlbumId(
                    albumId, requestLang, pageRequest));
        } else {
            throw new ResourceNotFoundException("There are no such items in album resource.");
        }
    }
}
