package com.ihordev.core.util;

import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PageableContentHelperTests implements WithBDDMockito {

    private Model model;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Before
    public void setUp() {
        model = mock(Model.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void shouldAddEntitiesPageAndNextPageUrlAttributeToModelWhenInitialSyncRequest() {
        String requestUrl = "http://localhost/entities";
        String expectedInitialNextPageUrl = "http://localhost/entities?page=1";
        String entitiesPageModelAttribute = "entities";
        String entitiesPageMainView = "entities";
        Slice<?> expectedEntitiesPage = mock(Slice.class);

        given(expectedEntitiesPage.getNumber()).willReturn(0);
        given(expectedEntitiesPage.hasNext()).willReturn(true);
        given(request.getRequestURL()).willReturn(new StringBuffer(requestUrl));

        PageableContentHelper pch = new PageableContentHelper();
        pch.set(model, request, response);

        String returnedViewName = pch.processRequest(entitiesPageMainView,
                entitiesPageModelAttribute, expectedEntitiesPage);

        verify(model, times(1)).addAttribute(eq(entitiesPageMainView), eq(expectedEntitiesPage));
        verify(model, times(1)).addAttribute(eq("initialNextPageUrl"), eq(expectedInitialNextPageUrl));
        Assert.assertEquals("entities", returnedViewName);
    }

    @Test
    public void shouldAddEntitiesPageToModelAndNextPageUrlLinkHeaderWhenScrolledPageAjaxRequest() {
        String requestUrl = "http://localhost/entities?page=1";
        String expectedLinkHeader = "<http://localhost/entities?page=2>; rel=next";
        String entitiesPageModelAttribute = "entities";
        String entitiesPageMainView = "entities";
        Slice<?> expectedEntitiesPage = mock(Slice.class);

        given(expectedEntitiesPage.getNumber()).willReturn(1);
        given(expectedEntitiesPage.hasNext()).willReturn(true);
        given(request.getHeader(eq("X-Requested-With"))).willReturn("XMLHttpRequest");
        given(request.getRequestURL()).willReturn(new StringBuffer(requestUrl));
        given(request.getQueryString()).willReturn("page=1");
        given(request.getParameter("page")).willReturn("1");

        PageableContentHelper pch = new PageableContentHelper();
        pch.set(model, request, response);

        String returnedViewName = pch.processRequest(entitiesPageMainView,
                entitiesPageModelAttribute, expectedEntitiesPage);

        verify(model, times(1)).addAttribute(eq(entitiesPageModelAttribute), eq(expectedEntitiesPage));
        verify(response, times(1)).addHeader(eq(HttpHeaders.LINK), eq(expectedLinkHeader));
        Assert.assertEquals("fragments/entities", returnedViewName);
    }

    @Test
    public void shouldAddEntitiesPageAndNextPageUrlAttributeToModelWhenAjaxItemContentPageRequest() {
        String requestUrl = "http://localhost/entities/1/subentities";
        String expectedInitialNextPageUrl = "http://localhost/entities/1/subentities?page=1";
        String entitiesPageModelAttribute = "subentities";
        String entitiesPageMainView = "subentities";
        Slice<?> expectedEntitiesPage = mock(Slice.class);

        given(expectedEntitiesPage.getNumber()).willReturn(0);
        given(expectedEntitiesPage.hasNext()).willReturn(true);
        given(request.getHeader(eq("X-Requested-With"))).willReturn("XMLHttpRequest");
        given(request.getRequestURL()).willReturn(new StringBuffer(requestUrl));

        PageableContentHelper pch = new PageableContentHelper();
        pch.set(model, request, response);

        String returnedViewName = pch.processRequest(entitiesPageMainView,
                entitiesPageModelAttribute, expectedEntitiesPage);

        verify(model, times(1)).addAttribute(eq(entitiesPageMainView), eq(expectedEntitiesPage));
        verify(model, times(1)).addAttribute(eq("initialNextPageUrl"), eq(expectedInitialNextPageUrl));
        Assert.assertEquals("subentities :: #main__content", returnedViewName);
    }
}
