package com.ihordev.util.navigation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;


public class NavigationHelperTests {

    private static MessageSource messageSource = Mockito.mock(MessageSource.class);
    private static NavigableHierarchyService navigableHierarchyService = Mockito.mock(NavigableHierarchyService.class);

    @BeforeClass
    public static void setUp()
    {
        when(messageSource.getMessage("Navigation.root", null, Locale.ENGLISH)).thenReturn("Main menu");
        when(messageSource.getMessage("Navigation.genres", null, Locale.ENGLISH)).thenReturn("Genres");
        when(messageSource.getMessage("Navigation.artists", null, Locale.ENGLISH)).thenReturn("Artists");
        when(messageSource.getMessage("Navigation.albums", null, Locale.ENGLISH)).thenReturn("Albums");

        when(navigableHierarchyService.getHierarchyLine("metal")).thenReturn(Arrays.asList("rock", "metal"));
    }

    @Test
    public void shouldCreateNavigationPathsForSimpleUrl() {
        String urlWithinApplication = "/genres/metal/artists/5/albums/2";
        List<PathNavigation> expectedNavPaths = Arrays.asList(
                new PathNavigation("Main menu", "/"),
                new PathNavigation("Genres",    "/genres"),
                new PathNavigation("temp",      "/genres/rock"),
                new PathNavigation("temp",      "/genres/metal"),
                new PathNavigation("Artists",   "/genres/metal/artists"),
                new PathNavigation("temp",      "/genres/metal/artists/5"),
                new PathNavigation("Albums",    "/genres/metal/artists/5/albums"),
                new PathNavigation("temp",      "/genres/metal/artists/5/albums/2")
        );

        NavigationHelper navigationHelper = new NavigationHelper.Builder(messageSource)
                .addHierarchicalSegment("genres", navigableHierarchyService)
            .build();

        List<PathNavigation> createdNavPaths = navigationHelper.createNavigationPaths(urlWithinApplication, Locale.ENGLISH);

        Assert.assertEquals(expectedNavPaths, createdNavPaths);
    }
/*
    @Test
    public void shouldCreateNavigationPathsForComplexUrl() {
        String urlWithinApplication = "/sample/collection1/one3/collection2/3/sample/other";
        List<String> expectedNavPaths = Arrays.asList(
                "/",
                "/sample",
                "/sample/collection1",
                "/sample/collection1/one1",
                "/sample/collection1/one2",
                "/sample/collection1/one3",
                "/sample/collection1/one3/collection2",
                "/sample/collection1/one3/collection2/1",
                "/sample/collection1/one3/collection2/2",
                "/sample/collection1/one3/collection2/3",
                "/sample/collection1/one3/collection2/3/sample",
                "/sample/collection1/one3/collection2/3/sample/other");

        NavigationHelper navigationHelper = new NavigationHelper();

        navigationHelper.addHierarchicalSegment("collection%20one",
                Arrays.asList("one1", "one2", "one3"));

        navigationHelper.addHierarchicalSegment("collection%20two",
                Arrays.asList("1", "2", "3"));

        List<String> createdNavPaths = navigationHelper.createNavigationPaths(urlWithinApplication);

        Assert.assertEquals(expectedNavPaths, createdNavPaths);
    }

    @Test
    public void shouldCreateNavigationPathsForRootUrl() {
        String urlWithinApplication = "/";
        List<String> expectedNavPaths = Arrays.asList("/");

        NavigationHelper navigationHelper = new NavigationHelper();

        List<String> createdNavPaths = navigationHelper.createNavigationPaths(urlWithinApplication);

        Assert.assertEquals(expectedNavPaths, createdNavPaths);
    }*/

}
