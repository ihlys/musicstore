package com.ihordev.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.util.Set;
import java.util.stream.Stream;


public class NamedQueriesAddingPostProcessor implements BeanPostProcessor {

    private Set<NamedQuery> namedQueries;

    public NamedQueriesAddingPostProcessor(Set<NamedQuery> namedQueries) {
        this.namedQueries = namedQueries;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EntityManager) {
            namedQueries.forEach(namedQuery -> createNamedQuery(namedQuery, (EntityManager) bean));
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
}
