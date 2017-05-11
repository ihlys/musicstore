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


@SuppressWarnings("uninitialized")
@Component
public class NavigationHelper {

    private RequestMapping requestMapping;

    @Autowired
    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }


    public List<MatchedRequestInfo> getInfoForMatchedRequestsPrecedingTo(HttpServletRequest request) {
        List<MatchedRequestInfo> matchedRequestsInfos = new ArrayList<>();

        HttpServletRequest currentRequestToCheck = request;

        while(!currentRequestToCheck.getRequestURI().isEmpty()) {
            RequestMatchingResult requestMatchingResult = requestMapping.matchRequest(currentRequestToCheck);
            if (requestMatchingResult.isRequestHasMatch()) {
                MatchedRequestInfo matchedRequestInfo = new MatchedRequestInfo(requestMatchingResult.getMatchedPattern(),
                        requestMatchingResult.getMatchedPathParams(),
                        currentRequestToCheck.getLocale(),
                        getFullUrl(currentRequestToCheck));
                matchedRequestsInfos.add(matchedRequestInfo);
            }

            currentRequestToCheck = createPreviousRequestFor(currentRequestToCheck);
        }

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
        fakeRequest.setContextPath(request.getContextPath());
        fakeRequest.setProtocol(request.getProtocol());
        fakeRequest.setPathInfo(request.getPathInfo());
        fakeRequest.setServletPath(request.getServletPath());

        return fakeRequest;
    }

}
