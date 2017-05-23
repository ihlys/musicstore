package com.ihordev.core.jpa.namedqueires;

import com.ihordev.core.jpa.namedqueries.NamedQueriesAddingPostProcessor;
import com.ihordev.core.jpa.namedqueries.NamedQueriesHelper;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Set;


@RunWith(SpringRunner.class)
public class NamedQueriesScannerTests implements WithBDDMockito {

    @TestConfiguration
    public static class Config implements WithBDDMockito {

        @Bean
        public static NamedQueriesAddingPostProcessor namedQueriesScanner() {
            Set<NamedQuery> namedQueries = NamedQueriesHelper.findNamedQueries("com.ihordev.core.util", ".*TestEntity");
            return new NamedQueriesAddingPostProcessor(namedQueries, null);
        }
    }

    @Repository
    @NamedQueries(value = {
            @NamedQuery(name = "TestQueryName1", query = "SELECT artist FROM Artist artist"),
            @NamedQuery(name = "TestQueryName2", query = "SELECT artist FROM Artist artist")
    })
    public static class TestEntity {}


    @Test
    public void test() {
        System.out.println("TEST ENDED");
    }
}
