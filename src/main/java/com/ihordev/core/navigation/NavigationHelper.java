package com.ihordev.core.navigation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ihordev.core.util.UrlUtils.cutSegmentFromEnd;
import static com.ihordev.core.util.UrlUtils.getFullUrl;

/**
 * <p>A class for retrieving information about http requests that can be responded
 * by application request mapping and precedes to specified request.
 * Request mapping is represented by {@link RequestMapping} class.
 */

@SuppressWarnings("initialization.fields.uninitialized")
@Component
public class NavigationHelper {

    private RequestMapping requestMapping;

    @Autowired
    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    /**
     * Creates a list of {@code MatchedRequestInfo} instances for http requests that
     * precedes current request according to application request mapping.
     * It continuously cuts a path segment from current request URI and creates a new
     * {@link MockHttpServletRequest} with this URI until request URI is empty. Each
     * created request is matched with {@link RequestMapping} and if it is matched
     * successfully, information about this request is added to result list.
     *
     * @param request  the http request parameter for which {@code MatchedRequestInfo}
     *                 list must be created
     * @return the list of {@code MatchedRequestInfo} instances containing information
     *         about matched requests that precedes to specified request
     */
    public List<MatchedRequestInfo> getInfoForMatchedRequestsPrecedingTo(HttpServletRequest request) {
        List<MatchedRequestInfo> matchedRequestsInfos = new ArrayList<>();

        HttpServletRequest currentRequestToCheck = request;

        while(!currentRequestToCheck.getRequestURI().isEmpty()) {
            RequestMatchingResult requestMatchingResult = requestMapping.matchRequest(currentRequestToCheck);
            if (requestMatchingResult.isRequestHasMatch()) {
                MatchedRequestInfo matchedRequestInfo = new MatchedRequestInfo(requestMatchingResult.getMatchedPattern(),
                        requestMatchingResult.getMatchedPathParams(),
                        currentRequestToCheck.getRequestURI());
                matchedRequestsInfos.add(matchedRequestInfo);
            }

            currentRequestToCheck = createPreviousRequestFor(currentRequestToCheck);
        }

        Collections.reverse(matchedRequestsInfos);

        return matchedRequestsInfos;
    }

    private MockHttpServletRequest createPreviousRequestFor(HttpServletRequest request) {
        MockHttpServletRequest fakeRequest = new MockHttpServletRequest();
        fakeRequest.setPreferredLocales(Collections.list(request.getLocales()));
        fakeRequest.setMethod(request.getMethod());
        fakeRequest.setRequestURI(cutSegmentFromEnd(request.getRequestURI()));
        fakeRequest.setScheme(request.getScheme());
        fakeRequest.setServerName(request.getServerName());
        fakeRequest.setServerPort(request.getServerPort());
        fakeRequest.setProtocol(request.getProtocol());

        return fakeRequest;
    }
}
