package com.ihordev.service;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.domain.AlbumL10n;
import com.ihordev.domain.ArtistL10n;
import com.ihordev.domain.GenreL10n;
import com.ihordev.domain.SongL10n;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import static com.ihordev.config.AppProfiles.SERVICE_TESTS;


@RunWith(SpringRunner.class)
@ActiveProfiles(SERVICE_TESTS)
@DataJpaTest(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.ihordev.core.repositories.+")
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
public class EntityL10nServiceTests {

    @Autowired
    private EntityL10nService entityL10nService;

    @Test
    @DatabaseSetup("classpath:data/service/entityl10n-service/get-genrel10n-source-data.xml")
    public void getGenreL10nTest() {
        GenreL10n genreL10nEn = entityL10nService.getGenreL10n(1L, "en");

        Assert.assertEquals("Genre One", genreL10nEn.getName());
        Assert.assertEquals("This is Genre One", genreL10nEn.getDescription());

        GenreL10n genreL10nOther = entityL10nService.getGenreL10n(1L, "other");

        Assert.assertEquals("Genre One", genreL10nOther.getName());
        Assert.assertEquals("This is Genre One", genreL10nOther.getDescription());
    }

    @Test
    @DatabaseSetup("classpath:data/service/entityl10n-service/get-artistl10n-source-data.xml")
    public void getArtistL10nTest() {
        ArtistL10n artistL10n = entityL10nService.getArtistL10n(1L, "en");

        Assert.assertEquals("Artist One", artistL10n.getName());
        Assert.assertEquals("This is Artist One", artistL10n.getDescription());

        ArtistL10n artistL10nOther = entityL10nService.getArtistL10n(1L, "other");

        Assert.assertEquals("Artist One", artistL10nOther.getName());
        Assert.assertEquals("This is Artist One", artistL10nOther.getDescription());
    }

    @Test
    @DatabaseSetup("classpath:data/service/entityl10n-service/get-albuml10n-source-data.xml")
    public void getAlbumL10nTest() {
        AlbumL10n albumL10n = entityL10nService.getAlbumL10n(1L, "en");

        Assert.assertEquals("Album One", albumL10n.getName());
        Assert.assertEquals("This is Album One", albumL10n.getDescription());

        AlbumL10n albumL10nOther = entityL10nService.getAlbumL10n(1L, "other");

        Assert.assertEquals("Album One", albumL10nOther.getName());
        Assert.assertEquals("This is Album One", albumL10nOther.getDescription());
    }

    @Test
    @DatabaseSetup("classpath:data/service/entityl10n-service/get-songl10n-source-data.xml")
    public void getSongL10nTest() {
        SongL10n songL10n = entityL10nService.getSongL10n(1L, "en");

        Assert.assertEquals("Song One", songL10n.getName());

        SongL10n songL10nOther = entityL10nService.getSongL10n(1L, "other");

        Assert.assertEquals("Song One", songL10nOther.getName());
    }

}
