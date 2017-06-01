package com.ihordev.web.controllers;



import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.domainprojections.GenreAsPageItem;
import com.ihordev.domainprojections.SongAsPageItem;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
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
import static com.ihordev.web.controllers.GenreController.PathVars.GENRE_ID;
import static com.ihordev.web.controllers.GenreController.PathVars.collectionToShowValues.*;

@Controller
public class GenreController {

    public static class PathVars {
        public static final String GENRE_ID = "genreId";
        enum collectionToShowValues {SUBGENRES, ARTISTS, SONGS }
    }

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @GetMapping(value = "/**/genres")
    public String genres(Model model, HttpServletRequest request, Pageable pageRequest) {
        addGenreSubGenresInfoToModel(model, request, null, pageRequest);
        return "music-content";
    }

    @GetMapping(value = {"/**/genres/{"+GENRE_ID+"}", "/**/genres/{"+GENRE_ID+"}/{collectionToShow}"})
    public String genre(@PathVariable Long genreId,
                        @PathVariable(required = false) String collectionToShow,
                        Model model,
                        HttpServletRequest request,
                        Pageable pageRequest) {
        if (collectionToShow != null) {
            if (SUBGENRES.name().equalsIgnoreCase(collectionToShow)) {
                addGenreSubGenresInfoToModel(model, request, genreId, pageRequest);
            } else if (ARTISTS.name().equalsIgnoreCase(collectionToShow)) {
                addGenreArtistsInfoToModel(model, request, genreId, pageRequest);
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addGenreSongsInfoToModel(model, request, genreId, pageRequest);
            } else {
                throw new ResourceNotFoundException("There are no such items in genre resource");
            }
        } else {
            addGenreSubGenresInfoToModel(model, request, genreId, pageRequest);
        }
        return "music-content";
    }

    private void addGenreSubGenresInfoToModel(Model model, HttpServletRequest request, Long genreId, Pageable pageRequest) {
        Slice<GenreAsPageItem> genresSubGenresPage = genreService.findSubGenresByParentGenreIdProjectedPaginated(
                request.getLocale().getLanguage(), genreId, pageRequest);
        model.addAttribute("genresPage", genresSubGenresPage);
        model.addAttribute("musicEntitiesPageView", "genresPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                String.valueOf(genresSubGenresPage.getNumber() + 1)));
    }

    private void addGenreArtistsInfoToModel(Model model, HttpServletRequest request, Long genresId, Pageable pageRequest) {
        Slice<ArtistAsPageItem> genresArtistsPage = artistService.findArtistsByGenreIdProjectedPaginated(
                request.getLocale().getLanguage(), genresId, pageRequest);
        model.addAttribute("artistsPage", genresArtistsPage);
        model.addAttribute("musicEntitiesPageView", "artistsPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                String.valueOf(genresArtistsPage.getNumber() + 1)));
    }

    private void addGenreSongsInfoToModel(Model model, HttpServletRequest request, Long genresId, Pageable pageRequest) {
        Slice<SongAsPageItem> genresSongsPage = songService.findSongsByGenreIdProjectedPaginated(
                request.getLocale().getLanguage(), genresId, pageRequest);
        model.addAttribute("songsPage", genresSongsPage);
        model.addAttribute("musicEntitiesPageView", "songsPage");
        model.addAttribute("nextPageUrl", rewriteQueryParameter(getFullUrl(request), "page",
                String.valueOf(genresSongsPage.getNumber() + 1)));
    }

}
