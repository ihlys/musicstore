package com.ihordev.core.navigation;

import com.ihordev.domain.Album;
import com.ihordev.domain.Artist;
import com.ihordev.service.AlbumService;
import com.ihordev.service.ArtistService;
import com.ihordev.service.GenreService;
import com.ihordev.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.ihordev.web.controllers.AlbumsController.PathVars.ALBUMS_ID;
import static com.ihordev.web.controllers.ArtistsController.PathVars.ARTISTS_ID;
import static com.ihordev.web.controllers.GenresController.PathVars.GENRES_ID;
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

        return createNavigationLinksFrom(matchedRequestsInfos);
    }

    private List<NavigationLink> createNavigationLinksFrom(List<MatchedRequestInfo> matchedRequestInfos) {
        return matchedRequestInfos.stream()
                    .map(this::createNavigationLink)
                    .collect(toList());
    }

    private NavigationLink createNavigationLink(MatchedRequestInfo matchedRequestInfo) {
        if (!matchedRequestInfo.getMatchedPathParams().isEmpty()) {
            return createDynamicResourceLink(matchedRequestInfo);
        } else {
            return createStaticResourceLink(matchedRequestInfo);
        }
    }

    private NavigationLink createDynamicResourceLink(MatchedRequestInfo matchedRequestInfo) {
        Map<String, String> matchedPathParams = matchedRequestInfo.getMatchedPathParams();
        if (matchedPathParams.containsKey(ALBUMS_ID)) {
            Album album = albumService.findById(parseLong(matchedPathParams.get(ALBUMS_ID)));
            return new NavigationLink(matchedRequestInfo.getRequestURL(), album.getName());
        } else if (matchedPathParams.containsKey(ARTISTS_ID)) {
            Artist artist = artistService.findById(parseLong(matchedPathParams.get(ARTISTS_ID)));
            return new NavigationLink(matchedRequestInfo.getRequestURL(), "asd"/*artist.getName()*/);
        } else if (matchedPathParams.containsKey(GENRES_ID)) {
            throw new AssertionError("not implemented yet");
        } else {
            String errMsg = format("Cannot create navigation link for dynamic resource: " +
                    "there is no handling for request with path variables: %s",
                    matchedPathParams.keySet());
            throw new NavigationException(errMsg);
        }
    }

    private NavigationLink createStaticResourceLink(MatchedRequestInfo matchedRequestInfo) {
        String label = messageSource.getMessage(matchedRequestInfo.getMatchedPattern(), new Object[]{}, matchedRequestInfo.getLocale());
        return new NavigationLink(matchedRequestInfo.getRequestURL(), label);
    }

}
