package com.ihordev.core.navigation;

import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

import static com.ihordev.testutils.MatcherUtils.requestWithURI;
import static java.util.Arrays.asList;


public class NavigationHelperTests implements WithBDDMockito {

    private RequestMapping requestMapping;

    @Before
    public void setUp() {
        requestMapping = mock(RequestMapping.class);
    }


    @Test
    public void shouldCreateRequestInfosForEveryRequestPreviousToInitialAccordingToRequestMapping() throws Exception {
        MockHttpServletRequest initialRequest = createMockGetRequest("/genres/15/artists");

        List<MatchedRequestInfo> expectedMatchedRequestInfos = new ArrayList<>();

        String request1URI = "/genres/15/artists";
        String request1MatchedPattern = "/**/genres/{genresId}/{collectionToShow}";
        Map<String, String> request1PathVars = new HashMap<>();
        request1PathVars.put("genresId", "15");
        request1PathVars.put("collectionToShow", "genres");
        MatchedRequestInfo request1Info = new MatchedRequestInfo(request1MatchedPattern, request1PathVars, request1URI);
        expectedMatchedRequestInfos.add(request1Info);

        String request2URI = "/genres/15";

        String request3URI = "/genres";
        String request3MatchedPattern = "/**/genres";
        MatchedRequestInfo request3Info = new MatchedRequestInfo(request3MatchedPattern,
                Collections.emptyMap(), request3URI);
        expectedMatchedRequestInfos.add(request3Info);

        String request4URI = "/";
        String request4MatchedPattern = "/";
        MatchedRequestInfo request4Info = new MatchedRequestInfo(request4MatchedPattern,
                Collections.emptyMap(), request4URI);
        expectedMatchedRequestInfos.add(request4Info);

        Collections.reverse(expectedMatchedRequestInfos);

        doReturn(new RequestMatchingResult(request1MatchedPattern, request1PathVars)).when(requestMapping)
                .matchRequest(argThat(requestWithURI(request1URI)));
        doReturn(new RequestMatchingResult(null, null)).when(requestMapping)
                .matchRequest(argThat(requestWithURI(request2URI)));
        doReturn(new RequestMatchingResult(request3MatchedPattern, Collections.emptyMap())).when(requestMapping)
                .matchRequest(argThat(requestWithURI(request3URI)));
        doReturn(new RequestMatchingResult(request4MatchedPattern, Collections.emptyMap())).when(requestMapping)
                .matchRequest(argThat(requestWithURI(request4URI)));

        NavigationHelper navigationHelper = new NavigationHelper();
        navigationHelper.setRequestMapping(requestMapping);

        List<MatchedRequestInfo> actualMatchedRequestInfos =
                navigationHelper.getInfoForMatchedRequestsPrecedingTo(initialRequest);

        InOrder inOrder = inOrder(requestMapping);
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURI(request1URI)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURI(request2URI)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURI(request3URI)));
        inOrder.verify(requestMapping, times(1)).matchRequest(argThat(requestWithURI(request4URI)));

        Assert.assertEquals(expectedMatchedRequestInfos, actualMatchedRequestInfos);
    }

    private MockHttpServletRequest createMockGetRequest(String requestURI) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI(requestURI);
        mockHttpServletRequest.setMethod("GET");
        mockHttpServletRequest.setPreferredLocales(asList(Locale.ENGLISH));
        return mockHttpServletRequest;
    }
}
