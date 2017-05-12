package com.ihordev.repository;

import com.ihordev.domain.Artist;
import com.ihordev.domain.ArtistsLocalizedData;
import com.ihordev.domain.Language;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ArtistRepositoryTests {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void shouldSaveAnArtistWithoutCascadingData() {
        Artist artist = new Artist("small image url", "large image url");
        artistRepository.save(artist);

        assertTrue(em.getEntityManager().contains(artist));
        assertNotNull(artist.getId());
    }

    @Test
    public void shouldUpdateAnArtistWithoutCascadingData() {
        final String originalImageSml = "small image url";
        final String originalImageLg = "large image url";
        final String updatedImageSml = "small image updated url";
        final String updatedImageLg = "large image updated url";

        Artist artist = new Artist(originalImageSml, originalImageLg);
        em.persist(artist);

        artist.setImageSmlUrl(updatedImageSml);
        artist.setImageLgUrl(updatedImageLg);

        artistRepository.save(artist);

        assertEquals(artist.getImageSmlUrl(), updatedImageSml);
        assertEquals(artist.getImageLgUrl(), updatedImageLg);
    }

    @Test
    public void shouldUpdateAnArtistWithCascading() {
        Language languageEn = new Language("EN");
        Language languageRu = new Language("RU");
        em.persist(languageEn);
        em.persist(languageRu);

        Artist artist = new Artist("small image url", "large image url");
        em.persist(artist);

        em.flush();
        em.detach(artist);

        ArtistsLocalizedData artistOneLocalizedDataEn =
                new ArtistsLocalizedData("Artist One", "Artist One description", artist, languageEn);
        ArtistsLocalizedData artistOneLocalizedDataRu =
                new ArtistsLocalizedData("Исполнитель Один", "Описание исполнителя Один", artist, languageRu);
        artist.getLocalizedDataSet().add(artistOneLocalizedDataEn);
        artist.getLocalizedDataSet().add(artistOneLocalizedDataRu);

        artistRepository.save(artist);

        assertNotNull(artist.getId());
        assertNotNull(em.find(Artist.class, artist.getId()));
    }

    @Test
    public void shouldSaveAnArtistX() {
        Language languageEn = new Language("EN");
        Language languageRu = new Language("RU");

        em.persist(languageEn);
        em.persist(languageRu);
        em.flush();

        Artist artist = new Artist("small image url", "large image url");
        artistRepository.save(artist);

        ArtistsLocalizedData artistOnesLocalizedDataEn =
                new ArtistsLocalizedData("Artist One", "Artist One description", artist, languageEn);
        ArtistsLocalizedData artistOnesLocalizedDataRu =
                new ArtistsLocalizedData("Исполнитель Один", "Описание исполнителя Один", artist, languageRu);
        artist.getLocalizedDataSet().add(artistOnesLocalizedDataEn);
        artist.getLocalizedDataSet().add(artistOnesLocalizedDataRu);

        artistRepository.save(artist);
    }

}
