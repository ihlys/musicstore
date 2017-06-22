package com.ihordev.core.navigation;

import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.SoundtrackL10n;
import com.ihordev.domain.ThematicCompilationL10n;
import com.ihordev.service.EntityL10nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.ihordev.core.util.UrlUtils.setPathParam;
import static com.ihordev.web.PathVariables.*;
import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * <p>A class for creating {@code NavigationLink} instances for site navigation
 * (also known as breadcrumbs) for specified http request. It tries to create
 * navigation structure from request URI string assuming that every navigation
 * point preceding to current has request mapping to one of the requests that
 * can be created by cutting a path segments from current request URI.
 *
 * <p>For example, produced results, depending on request mappings, could be
 * like this:
 * <pre>
 *   Request URI                  Navigation
 *   /resources                   /
 *                                /resources
 *
 *   /resources/1/pathvar         /
 *                                /resources
 *                                /resources/1
 *                                /resources/1/pathvar
 *
 *   /resources/1/subresources/2  /
 *                                /resources
 *                                /resources/1/subresources
 *                                /resources/1/subresources/2
 * </pre>
 * <p>Each {@link NavigationLink} can be a static resource or dynamic resource.
 * Static resource always exists and has a label with value from
 * {@link MessageSource}. Dynamic resource, like resource with URI "/resources/1",
 * has label with value obtained from {@link EntityL10nService}.
 */

@SuppressWarnings("initialization.fields.uninitialized")
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

    /**
     * Creates a list of {@code NavigationLink} instances for http request.
     *
     * @param request  the http request parameter for which {@code NavigationLink}
     *                 list must be created
     * @return the list containing {@code NavigationLink} instances
     */
    public List<NavigationLink> getNavigationLinks(HttpServletRequest request) {
        List<MatchedRequestInfo> matchedRequestsInfos = navigationHelper.getInfoForMatchedRequestsPrecedingTo(request);

        Locale requestLocale = request.getLocale();
        String requestLanguage = requestLocale.getLanguage();

        List<NavigationLink> navigationLinks = new ArrayList<>();
        for (MatchedRequestInfo matchedRequestInfo : matchedRequestsInfos) {
            String requestURL = matchedRequestInfo.getRequestURI();
            Map<String, String> matchedPathParams = matchedRequestInfo.getMatchedPathParams();
            if (!matchedPathParams.isEmpty()) {
                if (matchedPathParams.containsKey(GENRE_ID)) {
                    long genreId = parseLong(matchedPathParams.get(GENRE_ID));
                    List<NavigationLink> genreNavigationLinks = createGenreNavigationLinks(genreId,
                            requestURL, requestLanguage);
                    navigationLinks.addAll(genreNavigationLinks);
                } else {
                    navigationLinks.add(createDynamicResourceLink(matchedPathParams, requestURL, requestLanguage));
                }
            } else {
                navigationLinks.add(createStaticResourceLink(matchedRequestInfo, requestLocale));
            }
        }
        return navigationLinks;
    }


    private NavigationLink createDynamicResourceLink(Map<String, String> matchedPathParams, String requestURL,
                                                     String clientLanguage) {
        if (matchedPathParams.containsKey(ALBUM_ID)) {
            long albumId = parseLong(matchedPathParams.get(ALBUM_ID));
            AlbumL10n albumL10n = entityL10nService.getAlbumL10n(albumId, clientLanguage);
            return new NavigationLink(requestURL, albumL10n.getName());
        } else if (matchedPathParams.containsKey(ARTIST_ID)) {
            long artistId = parseLong(matchedPathParams.get(ARTIST_ID));
            ArtistL10n artistL10n = entityL10nService.getArtistL10n(artistId, clientLanguage);
            return new NavigationLink(requestURL, artistL10n.getName());
        } else if (matchedPathParams.containsKey(SOUNDTRACK_ID)) {
            long soundtrackId = parseLong(matchedPathParams.get(SOUNDTRACK_ID));
            SoundtrackL10n soundtrackL10n = entityL10nService.getSoundtrackL10n(soundtrackId, clientLanguage);
            return new NavigationLink(requestURL, soundtrackL10n.getName());
        } else if (matchedPathParams.containsKey(THEMATIC_COMPILATION_ID)) {
            long thematicCompilationId = parseLong(matchedPathParams.get(THEMATIC_COMPILATION_ID));
            ThematicCompilationL10n thematicCompilationL10n =
                    entityL10nService.getThematicCompilationL10n(thematicCompilationId, clientLanguage);
            return new NavigationLink(requestURL, thematicCompilationL10n.getName());
        } else {
            String errMsg = format("Cannot create navigation link for dynamic resource: " +
                    "there is no handling for request with path variables: %s",
                    matchedPathParams.keySet());
            throw new NavigationException(errMsg);
        }
    }

    private List<NavigationLink> createGenreNavigationLinks(long genreId, String requestURL, String clientLanguage) {
        return entityL10nService.getAllSuperGenresL10n(genreId, clientLanguage).stream()
                .map(genreL10n -> {
                    Long currentGenreId = genreL10n.getGenre().getId();
                    String navigationLinkUrl = setPathParam(requestURL, "genres", currentGenreId.toString());
                    return new NavigationLink(navigationLinkUrl, genreL10n.getName());
                })
                .collect(toList());
    }

    private NavigationLink createStaticResourceLink(MatchedRequestInfo matchedRequestInfo, Locale clientLocale) {
        String label = messageSource.getMessage(matchedRequestInfo.getMatchedPattern(), new Object[]{}, clientLocale);
        return new NavigationLink(matchedRequestInfo.getRequestURI(), label);
    }
}
