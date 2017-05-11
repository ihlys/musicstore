package com.ihordev.core.navigation;

import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Stream;

import static com.ihordev.core.navigation.NavigationHelperTests.PathVar.var;
import static com.ihordev.core.util.UrlUtils.getFullUrl;
import static java.util.Arrays.asList;


public class NavigationHelperTests implements WithBDDMockito {

    private RequestMapping requestMapping;
    private Locale locale = Locale.ENGLISH;

    @Before
    public void setUp() {
        requestMapping = mock(RequestMapping.class);
    }


    @Test
    public void shouldCreateRequestInfosForEveryRequestPreviousToInitialAccordingToRequestMapping() throws Exception {
        MockHttpServletRequest initialRequest = createMockGetRequest("/genres/15/artists");

        List<MatchedRequestInfo> expectedMatchedRequestInfos = new ArrayList<>();

        String request1URL = "http://localhost/genres/15/artists";
        String request1MatchedPattern = "/**/genres/{genresId}/{collectionToShow}";
        Map<String, String> request1PathVars = pathVars(var("genresId", "15"), var("collectionToShow", "genres"));
        MatchedRequestInfo request1Info = new MatchedRequestInfo(request1MatchedPattern, request1PathVars, locale, request1URL);
        expectedMatchedRequestInfos.add(request1Info);

        String request2URL = "http://localhost/genres/15";
        String request2MatchedPattern = "/**/genres/{genresId}";
        Map<String, String> request2PathVars = pathVars(var("genresId", "15"));
        MatchedRequestInfo request2Info = new MatchedRequestInfo(request2MatchedPattern, request2PathVars, locale, request2URL);
        expectedMatchedRequestInfos.add(request2Info);

        String request3URL = "http://localhost/genres";
        String request3MatchedPattern = "/**/genres";
        MatchedRequestInfo request3Info = new MatchedRequestInfo(request3MatchedPattern, null, locale, request3URL);
        expectedMatchedRequestInfos.add(request3Info);

        String request4URL = "http://localhost/";
        String request4MatchedPattern = "/";
        MatchedRequestInfo request4Info = new MatchedRequestInfo(request4MatchedPattern, null, locale, request4URL);
        expectedMatchedRequestInfos.add(request4Info);

        doReturn(new RequestMatchingResult(true, request1MatchedPattern, request1PathVars)).when(requestMapping).matchRequest(argThat(requestWithURL(request1URL)));
        doReturn(new RequestMatchingResult(true, request2MatchedPattern, request2PathVars)).when(requestMapping).matchRequest(argThat(requestWithURL(request2URL)));
        doReturn(new RequestMatchingResult(true, request3MatchedPattern, null)).when(requestMapping).matchRequest(argThat(requestWithURL(request3URL)));
        doReturn(new RequestMatchingResult(true, request4MatchedPattern, null)).when(requestMapping).matchRequest(argThat(requestWithURL(request4URL)));

        NavigationHelper navigationHelper = new NavigationHelper();
        navigationHelper.setRequestMapping(requestMapping);

        List<MatchedRequestInfo> actualMatchedRequestInfos = navigationHelper.getInfoForMatchedRequestsPrecedingTo(initialRequest);

        InOrder inOrder = inOrder(requestMapping);
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURL(request1URL)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURL(request2URL)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURL(request3URL)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURL(request4URL)));

        Assert.assertEquals(expectedMatchedRequestInfos, actualMatchedRequestInfos);
    }

    private MockHttpServletRequest createMockGetRequest(String requestURI) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(requestURI);
        mockHttpServletRequest.setMethod("GET");
        mockHttpServletRequest.setPreferredLocales(asList(Locale.ENGLISH));
        return mockHttpServletRequest;
    }

    private Map<String, String> pathVars(PathVar... pathVars) {
        return Stream.of(pathVars).collect(HashMap::new, PathVar::putIntoMap, Map::putAll);
    }

    private ArgumentMatcher<HttpServletRequest> requestWithURL(String requestURL) {
        return request -> getFullUrl(request).equals(requestURL);
    }

    public static class PathVar {
        private String key;
        private String value;

        private PathVar(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return key;
        }

        public static PathVar var(String key, String value) {
            return new PathVar(key, value);
        }

        public static void putIntoMap(Map<String, String> map, PathVar pathVar) {
            map.put(pathVar.key, pathVar.value);
        }
    }
}
