package com.ihordev.core.jpa.namedqueries;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.persistence.EntityManager;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;


public class NamedQueriesAddingPostProcessor implements BeanPostProcessor {

    private Set<NamedQuery> namedQueries = new HashSet<>();
    private Set<NamedNativeQuery> namedNativeQueries = new HashSet<>();

    public NamedQueriesAddingPostProcessor(Set<NamedQuery> namedQueries) {
        this.namedQueries = namedQueries;
    }

    public NamedQueriesAddingPostProcessor(Set<NamedQuery> namedQueries,
                                           Set<NamedNativeQuery> namedNativeQueries) {
        this.namedQueries = namedQueries;
        this.namedNativeQueries = namedNativeQueries;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EntityManager) {
            namedQueries.forEach(namedQuery -> createNamedQuery(namedQuery, (EntityManager) bean));
            namedNativeQueries.forEach(namedNativeQuery ->
                    createNamedNativeQuery(namedNativeQuery, (EntityManager) bean));
        }

        return bean;
    }

    private void createNamedQuery(NamedQuery namedQueryAnnotation, EntityManager entityManager) {
        Query query = entityManager.createQuery(namedQueryAnnotation.query());

        Stream.of(namedQueryAnnotation.hints()).forEach(queryHint ->
                query.setHint(queryHint.name(), queryHint.value())
        );

        query.setLockMode(namedQueryAnnotation.lockMode());

        entityManager.getEntityManagerFactory().addNamedQuery(namedQueryAnnotation.name(), query);
    }

    private void createNamedNativeQuery(NamedNativeQuery namedNativeQueryAnnotation, EntityManager entityManager) {
        Query query;

        if (namedNativeQueryAnnotation.resultSetMapping().isEmpty()) {
            query = entityManager.createNativeQuery(namedNativeQueryAnnotation.query(),
                    namedNativeQueryAnnotation.resultClass());
        } else {
            query = entityManager.createNativeQuery(namedNativeQueryAnnotation.query(),
                    namedNativeQueryAnnotation.resultSetMapping());
        }

        Stream.of(namedNativeQueryAnnotation.hints()).forEach(queryHint ->
                query.setHint(queryHint.name(), queryHint.value())
        );

        entityManager.getEntityManagerFactory().addNamedQuery(namedNativeQueryAnnotation.name(), query);
    }
}
