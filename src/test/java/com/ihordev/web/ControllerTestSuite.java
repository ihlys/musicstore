package com.ihordev.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    MainControllerTests.class,
    GenreControllerTests.class,
    ArtistControllerTests.class,
    AlbumControllerTests.class,
    SoundtrackControllerTests.class,
    ThematicCompilationControllerTests.class
})
public class ControllerTestSuite {}