package com.ihordev.core.navigation;

import com.ihordev.domain.*;
import com.ihordev.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.ihordev.web.controllers.AlbumController.PathVars.ALBUM_ID;
import static com.ihordev.web.controllers.ArtistController.PathVars.ARTIST_ID;
import static com.ihordev.web.controllers.GenreController.PathVars.GENRE_ID;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;


@SuppressWarnings("uninitialized")
@Component
public class Navigation {

    private GenreService    genreService;
    private ArtistService   artistService;
    private AlbumService    albumService;
    private SongService     songService;

    private MessageSource    messageSource;
    private NavigationHelper navigationHelper;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Autowired
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Autowired
    public void setAlbumService(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Autowired
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setNavigationHelper(NavigationHelper navigationHelper) {
        this.navigationHelper = navigationHelper;
    }

    public Navigation() {

    }

    public List<NavigationLink> getNavigationLinks(HttpServletRequest request) throws Exception {
        List<MatchedRequestInfo> matchedRequestsInfos = navigationHelper.getInfoForMatchedRequestsPrecedingTo(request);

        return createNavigationLinksFrom(matchedRequestsInfos, request.getLocale());
    }

    private List<NavigationLink> createNavigationLinksFrom(List<MatchedRequestInfo> matchedRequestsInfos,
                                                           Locale clientLocale) {
        return matchedRequestsInfos.stream()
                    .map(matchedRequestInfo -> createNavigationLink(matchedRequestInfo, clientLocale))
                    .collect(toList());
    }

    private NavigationLink createNavigationLink(MatchedRequestInfo matchedRequestInfo, Locale clientLocale) {
        if (!matchedRequestInfo.getMatchedPathParams().isEmpty()) {
            return createDynamicResourceLink(matchedRequestInfo, clientLocale.getLanguage());
        } else {
            return createStaticResourceLink(matchedRequestInfo, clientLocale);
        }
    }

    private NavigationLink createDynamicResourceLink(MatchedRequestInfo matchedRequestInfo, String clientLanguage) {
        Map<String, String> matchedPathParams = matchedRequestInfo.getMatchedPathParams();
        if (matchedPathParams.containsKey(ALBUM_ID)) {
            Album album = albumService.findById(parseLong(matchedPathParams.get(ALBUM_ID)));
            album.getAlbumL10nSet().stream()
                    .map(AlbumL10n::getName);
            return new NavigationLink(matchedRequestInfo.getRequestURL(), ""/*albumLocalizedData.getName()*/);
        } else if (matchedPathParams.containsKey(ARTIST_ID)) {
            //ArtistLocalizedData artistLocalizedData =
                //    artistLocalizedDataService.findById(parseLong(matchedPathParams.get(ARTIST_ID)));
            return new NavigationLink(matchedRequestInfo.getRequestURL(),"" /*artistLocalizedData.getName()*/);
        } else if (matchedPathParams.containsKey(GENRE_ID)) {
            throw new AssertionError("not implemented yet");
        } else {
            String errMsg = format("Cannot create navigation link for dynamic resource: " +
                    "there is no handling for request with path variables: %s",
                    matchedPathParams.keySet());
            throw new NavigationException(errMsg);
        }
    }

    private NavigationLink createStaticResourceLink(MatchedRequestInfo matchedRequestInfo, Locale clientLocale) {
        String label = messageSource.getMessage(matchedRequestInfo.getMatchedPattern(), new Object[]{}, clientLocale);
        return new NavigationLink(matchedRequestInfo.getRequestURL(), label);
    }

}
