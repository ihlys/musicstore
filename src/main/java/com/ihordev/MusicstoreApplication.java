package com.ihordev;

import com.ihordev.core.jpa.namedqueries.NamedQueriesAddingPostProcessor;
import com.ihordev.core.jpa.namedqueries.NamedQueriesHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import java.util.Set;

@SpringBootApplication
public class MusicstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicstoreApplication.class, args);
	}

    @Bean
    public static NamedQueriesAddingPostProcessor namedQueriesScanner() {
        Set<NamedQuery> namedQueries = NamedQueriesHelper.findNamedQueries("com.ihordev.repository", ".*Repository");
        Set<NamedNativeQuery> namedNativeQueries =
                NamedQueriesHelper.findNamedNativeQueries("com.ihordev.repository", ".*Repository");
        return new NamedQueriesAddingPostProcessor(namedQueries, namedNativeQueries);
    }
}
