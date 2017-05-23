package com.ihordev.web.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    MainControllerTests.class,
    GenreControllerTests.class,
    ArtistControllerTests.class,
    AlbumControllerTests.class
})
public class ControllerTestSuite {}