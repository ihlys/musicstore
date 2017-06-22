package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.service.SongService;
import com.ihordev.service.SoundtrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.web.PathVariables.SONGS;
import static com.ihordev.web.PathVariables.SOUNDTRACK_ID;


@Controller
public class SoundtrackController {

    @Autowired
    private Navigation navigation;

    @Autowired
    private PageableContentHelper pch;

    @Autowired
    private SoundtrackService soundtrackService;

    @Autowired
    private SongService songService;

    @GetMapping(value = "/**/soundtracks")
    public String soundtracks(Model model, HttpServletRequest request,
                              HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        pch.set(model, request, response);
        return pch.processRequest("soundtracks", "soundtracks", soundtrackService.findAllSoundtracksAsPageItems(
                request.getLocale().getLanguage(), pageRequest));
    }

    @GetMapping(value = "/**/soundtracks/{"+SOUNDTRACK_ID+"}/{collectionToShow}")
    public String soundtracks(@PathVariable Long soundtrackId, @PathVariable String collectionToShow, Model model,
                              HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        String requestLang = request.getLocale().getLanguage();
        model.addAttribute("currentMusicEntity", soundtrackService.findSoundtrackAsCurrentMusicEntityById(
                soundtrackId, requestLang));
        pch.set(model, request, response);
        if (SONGS.equals(collectionToShow)) {
            return pch.processRequest("songs", "songs", songService.findSongsAsPageItemsBySoundtrackId(
                    soundtrackId, requestLang, pageRequest));
        } else {
            throw new ResourceNotFoundException("There are no such items in soundtrack resource.");
        }
    }
}
