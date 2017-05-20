package com.ihordev.repository;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ihordev.core.jpa.projections.ProjectionFactory;
import com.ihordev.core.jpa.testing.DataTester;
import com.ihordev.core.jpa.testing.DataTesterCreator;
import com.ihordev.core.jpa.testing.Options;
import com.ihordev.core.util.GenericClass;
import com.ihordev.domain.Artist;
import com.ihordev.domainprojections.ArtistAsPageItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.core.jpa.testing.ListDataTester.RE_SORT_LISTS;
import static com.ihordev.core.jpa.testing.Options.Option.op;
import static com.ihordev.core.jpa.testing.Options.options;
import static java.lang.String.format;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       TransactionDbUnitTestExecutionListener.class })
@DatabaseSetup("classpath:repository/data/test-data.xml")
public class ArtistRepositoryTests {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private DataSource dataSource;

    @Test
    public void shouldSelectAllArtists() {
        ProjectionFactory<ArtistAsPageItem> projectionFactory = new ProjectionFactory<>(ArtistAsPageItem.class);

        List<ArtistAsPageItem> expectedArtists = new ArrayList<>();

        Map<String, Object> artist2Properties = new HashMap<>();
        artist2Properties.put("id", 2L);
        artist2Properties.put("imageSmlUrl", null);
        artist2Properties.put("name", "Disturbed");
        artist2Properties.put("description", "This is Disturbed band");
        expectedArtists.add(projectionFactory.createProjection(artist2Properties));

        Map<String, Object> artist1Properties = new HashMap<>();
        artist1Properties.put("id", 1L);
        artist1Properties.put("imageSmlUrl", null);
        artist1Properties.put("name", "Nightwish");
        artist1Properties.put("description", "This is Nightwish band");
        expectedArtists.add(projectionFactory.createProjection(artist1Properties));

        DataTesterCreator dataTesterCreator = new DataTesterCreator();
        DataTester<List<ArtistAsPageItem>> listDataTester = dataTesterCreator.createForClass(
                new GenericClass<List<ArtistAsPageItem>>(){}, options(op(RE_SORT_LISTS, true)));

        List<ArtistAsPageItem> actualArtists = artistRepository.findAllPaginated("EN");

        String failMsg = format("%1$s%1$sexpected:%1$s%1$s%2$s%1$s%1$sactual:%1$s%1$s%3$s", System.lineSeparator(),
                listDataTester.toString(expectedArtists), listDataTester.toString(actualArtists));
        Assert.assertTrue(failMsg, listDataTester.areEqual(expectedArtists, actualArtists));
    }

}
