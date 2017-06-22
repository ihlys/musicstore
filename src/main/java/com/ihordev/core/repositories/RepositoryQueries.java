package com.ihordev.core.repositories;

import org.checkerframework.checker.nullness.qual.PolyNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.core.repositories.QueryTemplateUtils.*;
import static java.util.stream.Collectors.toList;

/**
 * <p>A class with template repository methods for retrieving data. It's methods
 * use query templates and implements common data access logic as much as possible,
 * trying to preserve the possibility to customise some behavior.
 * <p>Depending on repository method, customisable options can include any of
 * options specified in list below:
 * <ol>
 *     <li>{@code queryResultClass} - the class of instances returned by
 *     repository method, can be DTO or Spring Data JPA projection;</li>
 *     <li>custom entity classes - the entities that is used in FROM clause
 *     of the query;</li>
 *     <li>queryArgsMap - the map containing query named parameters;</li>
 *     <li>customClausesMap - the map that contains custom JPQL query clauses.</li>
 * </ol>
 */
public class RepositoryQueries {

    private EntityManager entityManager;

    public RepositoryQueries(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final String LOCALIZED_QUERY_TEMPLATE =
        " SELECT [selectClause]                                                                                " +
        "   FROM [fromClauseStart] ? [entity] [entityAlias]!                                                   " +
        "   JOIN [entityAlias].[entityL10nAsProp] [entityL10nAlias]                                            " +
        "   JOIN [entityL10nAlias].language lang                                                               " +
        "   [fromClauseEnd]                                                                                    " +
        "   WHERE [whereClause] ? ([whereClause]) AND :!                                                       " +
        "         (lang.name = \\:language                                                                     " +
        "            OR lang.name = function('DEFAULT_LANGUAGE',)                                              " +
        "               AND NOT EXISTS (SELECT 1 FROM [entityL10n] [entityL10nAlias]                           " +
        "                                 WHERE [entityL10nAlias].[entityAsProp] = [entityAsProp]              " +
        "                                       AND [entityL10nAlias].language = (SELECT lang                  " +
        "                                                                           FROM Language lang         " +
        "                                                                           WHERE name = \\:language)))" +
        "   [orderByClause] ? ORDER BY [orderByClause] :!                                                      ";

    /**
     * <p>Retrieves page of specified entities using query template:
     *
     * <pre>
     * SELECT [selectClause]
     *   FROM [fromClauseStart] ? [entity] [entityAlias]!
     *   JOIN [entityAlias].[entityL10nAsProp] [entityL10nAlias]
     *   JOIN [entityL10nAlias].language lang
     *   [fromClauseEnd]
     *   WHERE [whereClause] ? ([whereClause]) AND :!
     *         (lang.name = \\:language
     *            OR lang.name = function('DEFAULT_LANGUAGE',)
     *               AND NOT EXISTS (SELECT 1 FROM [entityL10n] [entityL10nAlias]
     *                                 WHERE [entityL10nAlias].[entityAsProp] = [entityAsProp]
     *                                       AND [entityL10nAlias].language = (SELECT lang
     *                                                                           FROM Language lang
     *                                                                           WHERE name = \\:language)))
     *   [orderByClause] ? ORDER BY [orderByClause] :!
     * </pre>
     *
     * <p>Where:
     * <ol>
     *     <li>Query clauses "selectClause" and "orderByClause" are optional and by
     *         default automatically created by {@link QueryTemplateUtils}
     *         and substituted.</li>
     *     <li>Query clause "whereClause" is mandatory and must be provided by
     *         user.</li>
     *     <li>Template variables [{@code entity}, {@code entityAlias},
     *         {@code entityL10nAsProp}, {@code entityL10nAlias}, {@code entityL10n},
     *         {@code entityAsProp}] are created automatically by
     *         {@link QueryTemplateUtils} from entity classes in FROM clause
     *         that are passed as arguments to this method.</li>
     * </ol>
     *
     * @param queryResultClass  the class of returned instances. Can be DTO or
     *                          Spring Data JPA projection
     * @param entityClass  the primary entity that is queried for data
     * @param entityL10nClass  the localization entity to localize primary entity
     * @param customClausesMap  the map containing custom query clauses
     * @param queryArgsMap  the map containing named query parameters
     * @param pageRequest  the object that contains information about page
     * @param <T>  the returned instances type parameter
     *
     * @return page of entities instances
     */
    @SuppressWarnings({"unchecked", "argument.type.incompatible"})
    public <T> Slice<T> findEntitiesAsPageItems(Class<T> queryResultClass, Class<?> entityClass,
                                                Class<?> entityL10nClass, Map<String, String> customClausesMap,
                                                Map<String, @PolyNull Object> queryArgsMap, Pageable pageRequest) {
        List<EntityVariable> queryEntityVariablesList = new ArrayList<>();
        queryEntityVariablesList.add(new EntityVariable(entityClass, "entity"));
        queryEntityVariablesList.add(new EntityVariable(entityL10nClass, "entityL10n").appendToAsProp("Set"));

        Map<String, String> templateVariablesMap = new HashMap<>();
        templateVariablesMap.put("selectClause", createSelectClause(queryResultClass, queryEntityVariablesList));
        templateVariablesMap.put("orderByClause", createOrderByClause(pageRequest.getSort(), queryResultClass));
        templateVariablesMap.putAll(customClausesMap);
        templateVariablesMap.putAll(createTemplateVariablesForEntities(queryEntityVariablesList));

        String queryString = StringTemplater.resolve(LOCALIZED_QUERY_TEMPLATE, templateVariablesMap);

        Query query = entityManager.createQuery(queryString);
        queryArgsMap.entrySet().stream().forEach((Map.Entry<String, @PolyNull Object> entry) ->
                query.setParameter(entry.getKey(), entry.getValue()));

        query.setFirstResult(pageRequest.getOffset());
        // +1 result is used to determine if there are next slice without additional count query
        int pageSizePlusOne = pageRequest.getPageSize() + 1;
        query.setMaxResults(pageSizePlusOne);

        List<Object[]> resultList = query.getResultList();
        boolean hasNextSliceOfData = resultList.size() == pageSizePlusOne;

        List<T> pageItemsList = resultList.stream()
                .limit(pageRequest.getPageSize())
                .map(createRowMapper(queryResultClass))
                .collect(toList());

        return new SliceImpl<>(pageItemsList, pageRequest, hasNextSliceOfData);
    }

    /**
     * Retrieves one entity instance as current music entity by id using query
     * template:
     *
     * <pre>
     * SELECT [selectClause]
     *   FROM [fromClauseStart] ? [entity] [entityAlias]!
     *   JOIN [entityAlias].[entityL10nAsProp] [entityL10nAlias]
     *   JOIN [entityL10nAlias].language lang
     *   [fromClauseEnd]
     *   WHERE [whereClause] ? ([whereClause]) AND :!
     *         (lang.name = \\:language
     *            OR lang.name = function('DEFAULT_LANGUAGE',)
     *               AND NOT EXISTS (SELECT 1 FROM [entityL10n] [entityL10nAlias]
     *                                 WHERE [entityL10nAlias].[entityAsProp] = [entityAsProp]
     *                                       AND [entityL10nAlias].language = (SELECT lang
     *                                                                           FROM Language lang
     *                                                                           WHERE name = \\:language)))
     *   [orderByClause] ? ORDER BY [orderByClause] :!
     * </pre>
     *
     * <p>Where:
     * <ol>
     *     <li>Query clause "selectClause" are automatically created by
     *         {@link QueryTemplateUtils} and substituted;</li>
     *     <li>Query clause "whereClause" is resolved with nested template
     *         "{@code [entityAlias].id = \:[entityAsArg]Id}".</li>
     *     <li>Template variables [{@code entity}, {@code entityAlias},
     *         {@code entityL10nAsProp}, {@code entityL10nAlias}, {@code entityL10n},
     *         {@code entityAsProp}] are created automatically by
     *         {@link QueryTemplateUtils} from entity classes in FROM clause
     *         that are passed as arguments to this method.</li>
     * </ol>
     *
     * @param queryResultClass  the class of returned instance. Can DTO or
     *                          Spring Data JPA projection
     * @param entityClass  the primary entity that is queried for data
     * @param entityL10nClass  the localization entity to localize primary entity
     * @param queryArgsMap  the map containing named query parameters
     * @param <T>  the returned instances type parameter
     *
     * @return current music entity
     */
    public <T> T findCurrentMusicEntityById(Class<T> queryResultClass, Class<?> entityClass,
                                            Class<?> entityL10nClass, Map<String, Object> queryArgsMap) {
        List<EntityVariable> queryEntityVariablesList = new ArrayList<>();
        queryEntityVariablesList.add(new EntityVariable(entityClass, "entity"));
        queryEntityVariablesList.add(new EntityVariable(entityL10nClass, "entityL10n").appendToAsProp("Set"));

        Map<String, String> templateVariablesMap = new HashMap<>();
        templateVariablesMap.put("selectClause", createSelectClause(queryResultClass, queryEntityVariablesList));
        templateVariablesMap.putAll(createTemplateVariablesForEntities(queryEntityVariablesList));
        templateVariablesMap.put("whereClause", StringTemplater.resolve("[entityAlias].id = \\:[entityAsArg]Id",
                templateVariablesMap));

        String queryString = StringTemplater.resolve(LOCALIZED_QUERY_TEMPLATE, templateVariablesMap);

        Query query = entityManager.createQuery(queryString);
        queryArgsMap.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));

        return createRowMapper(queryResultClass).apply((Object[]) query.getSingleResult());
    }

    /**
     * Retrieves localization entity instance for entity by it's id using query
     * template:
     *
     * <pre>
     * SELECT [selectClause]
     *   FROM [fromClauseStart] ? [entity] [entityAlias]!
     *   JOIN [entityAlias].[entityL10nAsProp] [entityL10nAlias]
     *   JOIN [entityL10nAlias].language lang
     *   [fromClauseEnd]
     *   WHERE [whereClause] ? ([whereClause]) AND :!
     *         (lang.name = \\:language
     *            OR lang.name = function('DEFAULT_LANGUAGE',)
     *               AND NOT EXISTS (SELECT 1 FROM [entityL10n] [entityL10nAlias]
     *                                 WHERE [entityL10nAlias].[entityAsProp] = [entityAsProp]
     *                                       AND [entityL10nAlias].language = (SELECT lang
     *                                                                           FROM Language lang
     *                                                                           WHERE name = \\:language)))
     *   [orderByClause] ? ORDER BY [orderByClause] :!
     * </pre>
     *
     * <p>Where:
     * <ol>
     *     <li>Query clause "selectClause" are set with value returned from
     *         {@link EntityVariable#getAsAliasValue()};</li>
     *     <li>Query clause "whereClause" is resolved with nested template
     *         "{@code [entityAlias].id = \:[entityAsArg]Id}".</li>
     *     <li>Template variables [{@code entity}, {@code entityAlias},
     *         {@code entityL10nAsProp}, {@code entityL10nAlias}, {@code entityL10n},
     *         {@code entityAsProp}] are created automatically by
     *         {@link QueryTemplateUtils} from entity classes in FROM clause
     *         that are passed as arguments to this method.</li>
     * </ol>
     *
     * @param entityL10nClass  the class of entity localization.
     * @param entityClass  the entity for which localization entity must be found
     * @param queryArgsMap  the map containing named query parameters
     * @param <T>  the entity localization type parameter
     * @return page of entities instances
     */
    public <T> T findEntityL10n(Class<T> entityL10nClass, Class<?> entityClass, Map<String, Object> queryArgsMap) {
        List<EntityVariable> queryEntityVariablesList = new ArrayList<>();
        queryEntityVariablesList.add(new EntityVariable(entityClass, "entity"));
        EntityVariable entityL10nVar = new EntityVariable(entityL10nClass, "entityL10n").appendToAsProp("Set");
        queryEntityVariablesList.add(entityL10nVar);

        Map<String, String> templateVariablesMap = new HashMap<>();
        templateVariablesMap.put("selectClause", entityL10nVar.getAsAliasValue());
        templateVariablesMap.putAll(createTemplateVariablesForEntities(queryEntityVariablesList));
        templateVariablesMap.put("whereClause", StringTemplater.resolve("[entityAlias].id = \\:[entityAsArg]Id",
                templateVariablesMap));

        String queryString = StringTemplater.resolve(LOCALIZED_QUERY_TEMPLATE, templateVariablesMap);

        TypedQuery<T> query = entityManager.createQuery(queryString, entityL10nClass);
        queryArgsMap.entrySet().stream().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));

        return query.getSingleResult();
    }
}
