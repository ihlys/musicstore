package com.ihordev.web.controllers;



import com.ihordev.domain.Artist;
import com.ihordev.domain.Genre;
import com.ihordev.domain.Song;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
import com.ihordev.service.SongService;
import com.ihordev.web.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.ihordev.web.controllers.GenresController.PathVars.GENRES_ID;
import static com.ihordev.web.controllers.GenresController.PathVars.collectionToShowValues.*;

@Controller
public class GenresController {

    public static class PathVars {
        public static final String GENRES_ID = "genresId";
        enum collectionToShowValues { GENRES, ARTISTS, SONGS }
    }

    @Autowired
    private GenreService genreService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @RequestMapping(value = "/**/genres", method = RequestMethod.GET)
    public String genres(Model model) {
        List<Genre> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        return "genres";
    }

    @RequestMapping(value = {"/**/genres/{"+GENRES_ID+"}", "/**/genres/{"+GENRES_ID+"}/{collectionToShow}"},
                    method = RequestMethod.GET)
    public String genre(@PathVariable long genresId,
                        @PathVariable(required = false) String collectionToShow,
                        Model model) {
        if (collectionToShow != null) {
            if (GENRES.name().equalsIgnoreCase(collectionToShow)) {
                addGenresSubGenresToModel(model, genresId);
                return "genres";
            } else if (ARTISTS.name().equalsIgnoreCase(collectionToShow)) {
                addGenresArtistsToModel(model, genresId);
                return "artists";
            } else if(SONGS.name().equalsIgnoreCase(collectionToShow)) {
                addGenresSongsToModel(model, genresId);
                return "songs";
            } else {
                throw new ResourceNotFoundException("There are no such items in genre resource");
            }
        } else {
            addGenresSubGenresToModel(model, genresId);
            return "genres";
        }
    }

    private void addGenresSubGenresToModel(Model model, long genresId) {
        List<Genre> genresSubGenres = genreService.findSubGenres(genresId);
        model.addAttribute("genres", genresSubGenres);
    }

    private void addGenresArtistsToModel(Model model, long genresId) {
        List<Artist> genresArtists = artistService.findByGenre(genresId);
        model.addAttribute("artists", genresArtists);
    }

    private void addGenresSongsToModel(Model model, long genresId) {
        List<Song> genresSongs = songService.findByGenre(genresId);
        model.addAttribute("songs", genresSongs);
    }

}
