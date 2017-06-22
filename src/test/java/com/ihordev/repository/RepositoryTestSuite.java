package com.ihordev.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)

@Suite.SuiteClasses({
    AlbumRepositoryTests.class,
    GenreRepositoryTests.class,
    ArtistRepositoryTests.class,
    SongRepositoryTests.class,
    SoundtrackRepositoryTests.class,
    ThematicCompilationRepositoryTests.class
})
public class RepositoryTestSuite {}