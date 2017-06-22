package com.ihordev;

import com.ihordev.config.NamedQueriesAddingPostProcessor;
import com.ihordev.config.RequestMappingHandlerMappingPostProcessor;
import com.ihordev.core.util.NamedQueriesHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

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

    @Bean
    public static RequestMappingHandlerMappingPostProcessor suffixPatternMatchDisabler() {
        return new RequestMappingHandlerMappingPostProcessor();
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:i18n/messages", "classpath:i18n/navigation-links-labels");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(60);
        return messageSource;
    }
}
