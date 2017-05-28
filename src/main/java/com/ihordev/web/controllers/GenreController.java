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

import java.util.Locale;

import static com.ihordev.web.controllers.GenreController.PathVars.GENRE_ID;
import static com.ihordev.web.controllers.GenreController.PathVars.collectionToShowValues.*;

@Controller
public class GenreController {

    public static class PathVars {
        public static final String GENRE_ID = "genreId";
        enum collectionToShowValues { GENRES, ARTISTS, SONGS }
    }

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @GetMapping(value = "/**/genres")
    public String genres(Model model, Locale locale, Pageable pageRequest) {
        addGenreSubGenresToModel(model, locale.getLanguage(), null, pageRequest);
        return "genres";
    }

    @GetMapping(value = {"/**/genres/{"+GENRE_ID+"}", "/**/genres/{"+GENRE_ID+"}/{collectionToShow}"})
    public String genre(@PathVariable Long genreId,
                        @PathVariable(required = false) String collectionToShow,
                        Model model,
                        Locale locale,
                        Pageable pageRequest) {
        if (collectionToShow != null) {
            if (GENRES.name().equalsIgnoreCase(collectionToShow)) {
                addGenreSubGenresToModel(model, locale.getLanguage(), genreId, pageRequest);
                return "genres";
            } else if (ARTISTS.name().equalsIgnoreCase(collectionToShow)) {
                addGenreArtistsToModel(model, locale.getLanguage(), genreId, pageRequest);
                return "artists";
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addGenreSongsToModel(model, locale.getLanguage(), genreId, pageRequest);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in genre resource");
            }
        } else {
            addGenreSubGenresToModel(model, locale.getLanguage(), genreId, pageRequest);
            return "genres";
        }
    }

    private void addGenreSubGenresToModel(Model model, String clientLanguage, Long genreId, Pageable pageRequest) {
        Slice<GenreAsPageItem> genresSubGenres = genreService.findSubGenresByParentGenreIdProjectedPaginated(
                clientLanguage, genreId, pageRequest);
        model.addAttribute("genres", genresSubGenres);
    }

    private void addGenreArtistsToModel(Model model, String clientLanguage, Long genresId, Pageable pageRequest) {
        Slice<ArtistAsPageItem> genresArtists = artistService.findArtistsByGenreIdProjectedPaginated(
                clientLanguage, genresId, pageRequest);
        model.addAttribute("artists", genresArtists);
    }

    private void addGenreSongsToModel(Model model, String clientLanguage, Long genresId, Pageable pageRequest) {
        Slice<SongAsPageItem> genresSongs = songService.findSongsByGenreIdProjectedPaginated(
                clientLanguage, genresId, pageRequest);

        System.out.println("clientLanguage: " + clientLanguage);
        System.out.println("HERE: " + genresSongs);

        model.addAttribute("songs", genresSongs);
    }

}