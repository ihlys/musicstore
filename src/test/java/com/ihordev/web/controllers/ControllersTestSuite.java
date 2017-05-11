package com.ihordev.web.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    MainControllerTests.class,
    GenresControllerTests.class,
    ArtistsControllerTests.class,
    AlbumsControllerTests.class
})
public class ControllersTestSuite {}