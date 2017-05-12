package com.ihordev.repository;

import com.ihordev.domain.Artist;
import com.ihordev.domain.ArtistsLocalizedData;
import com.ihordev.domain.Language;
import com.ihordev.domainprojections.ArtistAsPageItem;
import com.ihordev.service.ArtistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Service.class))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SimpleTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ArtistService artistService;

/*    @Test
    public void test() {
        init();
        queries();
    }

    public void init() {
        Language languageEn = new Language("EN");
        Language languageRu = new Language("RU");

        em.persist(languageEn);
        em.persist(languageRu);
        em.flush();

        Artist artistKorn = new Artist();
        artistKorn.setImageSmlUrl("Small image of Korn");
        artistKorn.setImageLgUrl("Large image of Korn");

        artistService.saveArtist(artistKorn);

        artistKorn.getLocalizedDataSet().add(new ArtistsLocalizedData("Korn", "Very cool band!", artistKorn, languageEn));
        artistKorn.getLocalizedDataSet().add(new ArtistsLocalizedData("Korn", "Клевая группа!", artistKorn, languageRu));

        artistService.updateArtist(artistKorn);

        Artist artistNightwish = new Artist();
        artistNightwish.setImageSmlUrl("Small image of Nightwish");
        artistNightwish.setImageLgUrl("Large image of Nightwish");

        artistService.saveArtist(artistNightwish);

        artistNightwish.getLocalizedDataSet().add(new ArtistsLocalizedData("Nightwish", "Very cool band!", artistNightwish, languageEn));
        artistNightwish.getLocalizedDataSet().add(new ArtistsLocalizedData("Nightwish", "Клевая группа!", artistNightwish, languageRu));

        artistService.updateArtist(artistNightwish);
    }

    public void queries() {
        List<ArtistAsPageItem> allPaginated = artistService.findAllPaginated("EN");

        System.out.println(allPaginated);
    }*/

}
