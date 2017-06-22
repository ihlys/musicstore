package com.ihordev.web;

import com.ihordev.core.navigation.Navigation;
import com.ihordev.core.util.PageableContentHelper;
import com.ihordev.service.SongService;
import com.ihordev.service.ThematicCompilationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ihordev.web.PathVariables.SONGS;
import static com.ihordev.web.PathVariables.THEMATIC_COMPILATION_ID;


@Controller
public class ThematicCompilationController {

    @Autowired
    private Navigation navigation;

    @Autowired
    private PageableContentHelper pch;

    @Autowired
    private ThematicCompilationService thematicCompilationService;

    @Autowired
    private SongService songService;

    @GetMapping(value = "/**/thematic-compilations")
    public String thematicCompilations(Model model, HttpServletRequest request,
                                       HttpServletResponse response, Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        pch.set(model, request, response);
        return pch.processRequest("thematicCompilations", "thematic-compilations",
                thematicCompilationService.findAllThematicCompilationsAsPageItems(
                        request.getLocale().getLanguage(), pageRequest));
    }

    @GetMapping(value = "/**/thematic-compilations/{"+THEMATIC_COMPILATION_ID+"}/{collectionToShow}")
    public String thematicCompilation(@PathVariable Long thematicCompilationId, @PathVariable String collectionToShow,
                                       Model model, HttpServletRequest request, HttpServletResponse response,
                                       Pageable pageRequest) {
        model.addAttribute("navigation", navigation.getNavigationLinks(request));
        model.addAttribute("currentMusicEntity",
                thematicCompilationService.findThematicCompilationAsCurrentMusicEntityById(
                        thematicCompilationId, request.getLocale().getLanguage()));
        pch.set(model, request, response);
        if (SONGS.equals(collectionToShow)) {
            return pch.processRequest("songs", "songs", songService.findSongsAsPageItemsByThematicCompilationId(
                    thematicCompilationId, request.getLocale().getLanguage(), pageRequest));
        } else {
            throw new ResourceNotFoundException("There are no such items in thematicCompilation resource.");
        }
    }
}
