package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
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
public class GenreController {

    @Autowired
    private Navigation navigation;

    @Autowired
    private PageableContentHelper pch;

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @GetMapping(value = "/**/genres")
    public String genres(Model model, HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        pch.set(model, request, response);
        return pch.processRequest("genres", "genres", genreService.findGenresAsPageItemsByParentGenreId(
                null, request.getLocale().getLanguage(), pageRequest));
    }

    @GetMapping(value = "/**/genres/{"+GENRE_ID+"}/{collectionToShow}")
    public String genre(@PathVariable Long genreId, @PathVariable String collectionToShow, Model model,
                        HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        String requestLang = request.getLocale().getLanguage();
        model.addAttribute("currentMusicEntity", genreService.findGenreAsCurrentMusicEntityById(genreId, requestLang));
        pch.set(model, request, response);
        if (SUBGENRES.equals(collectionToShow)) {
            return pch.processRequest("genres", "genres", genreService.findGenresAsPageItemsByParentGenreId(
                    genreId, requestLang, pageRequest));
        } else if (ARTISTS.equals(collectionToShow)) {
            return pch.processRequest("artists", "artists", artistService.findArtistsAsPageItemsByGenreId(
                    genreId, requestLang, pageRequest));
        } else if(SONGS.equals(collectionToShow)) {
            return pch.processRequest("songs", "songs", songService.findSongsAsPageItemsByGenreId(
                    genreId, requestLang, pageRequest));
        } else {
            throw new ResourceNotFoundException("There are no such items in genre resource");
        }
    }

}
