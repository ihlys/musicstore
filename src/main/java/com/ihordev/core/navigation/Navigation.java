package com.ihordev.core.navigation;

import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.GenreL10n;
import com.ihordev.service.EntityL10nService;
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

    private EntityL10nService entityL10nService;
    private MessageSource     messageSource;
    private NavigationHelper  navigationHelper;

    @Autowired
    public void setEntityL10nService(EntityL10nService entityL10nService) {
        this.entityL10nService = entityL10nService;
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
            long albumId = parseLong(matchedPathParams.get(ALBUM_ID));
            AlbumL10n albumL10n = entityL10nService.getAlbumL10n(albumId, clientLanguage);
            return new NavigationLink(matchedRequestInfo.getRequestURL(), albumL10n.getName());
        } else if (matchedPathParams.containsKey(ARTIST_ID)) {
            long artistId = parseLong(matchedPathParams.get(ARTIST_ID));
            ArtistL10n artistL10n = entityL10nService.getArtistL10n(artistId, clientLanguage);
            return new NavigationLink(matchedRequestInfo.getRequestURL(), artistL10n.getName());
        } else if (matchedPathParams.containsKey(GENRE_ID)) {
            long genreId = parseLong(matchedPathParams.get(GENRE_ID));
            GenreL10n genreL10n = entityL10nService.getGenreL10n(genreId, clientLanguage);
            return new NavigationLink(matchedRequestInfo.getRequestURL(), genreL10n.getName());
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
