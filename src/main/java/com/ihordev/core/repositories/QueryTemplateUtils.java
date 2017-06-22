package com.ihordev.core.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ihordev.core.util.ReflectionsUtils.*;
import static java.lang.String.format;

/**
 * <p>A class with utility methods that allows to automatically create some
 * JPQL clauses and JPQL query template variables for entities.
 */

public final class QueryTemplateUtils {

    private QueryTemplateUtils() {
        throw new AssertionError("QueryTemplateHelper cannot be instantiated.");
    }

    /**
     * <p>Creates {@code SELECT} clause that corresponds to {@code queryResultClass}
     * properties. Created clause consists of columns with aliases equal to
     * {@code queryResultClass} properties names. If some properties of class are
     * dynamically calculated values then getters for this properties must have
     * {@link ColumnExpression} annotation specified.
     *
     * <p>It takes properties from {@code queryResultClass} and for each property
     * tries to find {@link EntityVariable} whose entity originally owns this property.
     * When it is found it generates "{@code entityAlias.property AS property}",
     * where {@code entityAlias} is received by calling
     * {@code entityVariable.getAsAliasValue()} method.

     * <p>If property from {@code queryResultClass} is annotated with
     * {@code ColumnExpression} annotation, then it generates
     * "{@code columnExpression AS property}", where columnExpression is received
     * from {@code ColumnExpression} annotation value.
     *
     * <p>When two {@code EntityVariable} instances have same properties, preference
     * will be given to first found. So, to choose from which {@code EntityVariable}
     * instance property with equal name must be taken one can use insertion order
     * in {@code queryEntityVariables}. If two {@code EntityVariable} instances have
     * multiple properties with same names and for each such property
     * {@code EntityVariable} instance must be chosen in different preference than
     * such complex {@code SELECT} clause must be written manually.
     *
     * <p><b>Note: properties are always sorted in alphabetic order.</b>
     *
     * <p>Example usage:
     * <p>Let's say {@code queryResultClass} is a Spring Data JPA projection:
     * <pre><code>
     * public interface SomeProjection {
     *
     *     String getFieldOne();
     *
     *     String getFieldTwo();
     *
     *    {@literal @}ColumnExpression(value = "expression")
     *     String getFieldThree();
     * }
     * </code></pre>
     *
     * and there are also two entities:
     *
     * <pre><code>
     *{@literal @}Entity
     * public class SomeEntityOne {
     *     private Object fieldOne;
     *     public Object getFieldOne() {
     *         return fieldOne;
     *     }
     * }
     *{@literal @}Entity
     * public class SomeEntityTwo {
     *     private Object fieldTwo;
     *     public Object getFieldTwo() {
     *         return fieldTwo;
     *     }
     * }
     * </code></pre>
     *
     * then:
     *
     * <pre>{@code
     * List<EntityVariable> queryEntityVariables = new ArrayList<>();
     * queryEntityVariables.add(new EntityVariable(SomeEntityOne.class, "entityOne"));
     * queryEntityVariables.add(new EntityVariable(SomeEntityTwo.class, "entityTwo"));
     *
     * String createdSelectClause = createSelectClause(SomeProjection.class,
     *          queryEntityVariables);
     * }</pre>
     *
     * Value of {@code createdSelectClause} would be: "{@code someEntityOne.fieldOne AS fieldOne,
     * expression AS fieldThree, someEntityOne.fieldTwo AS fieldTwo"}
     *
     * @param <T> the query result class type parameter
     * @param queryResultClass  the class that is used as return type of query
     * @param queryEntityVariables  the list that contains {@link EntityVariable}
     *                              instances of query
     *
     * @return the created {@code SELECT} clause string
     *
     * @throws IllegalArgumentException if property from {@code queryResultClass} cannot be found in
     *         any {@code EntityVariable} specified in {@code queryEntityVariables}
     */
    public static <T> String createSelectClause(Class<T> queryResultClass, List<EntityVariable> queryEntityVariables) {
        SortedSet<Method> propertyGettersMethods = getPropertyGettersMethodsFromClass(queryResultClass,
                       Comparator.comparing(Method::getName));

        return propertyGettersMethods.stream()
                .map(propertyGetterMethod -> {
                    String propertyName = getPropertyName(propertyGetterMethod);
                    ColumnExpression ceAnnotation = propertyGetterMethod.getAnnotation(ColumnExpression.class);
                    if (ceAnnotation != null) {
                        return format("%1$s AS %2$s", ceAnnotation.value(), propertyName);
                    } else {
                        for (EntityVariable entityVariable : queryEntityVariables) {
                            if (entityVariable.getProperties().contains(propertyName)) {
                                return format("%1$s.%2$s AS %2$s", entityVariable.getAsAliasValue(), propertyName);
                            }
                        }
                        String errMsg = format("Cannot find property %1$s from result class %2$s in any entity " +
                                "variable %3$s", propertyName, queryResultClass, queryEntityVariables);
                        throw new IllegalArgumentException(errMsg);
                    }
                })
                .collect(Collectors.joining(", "));
    }

    /**
     * <p>Creates {@code GROUP BY} clause that corresponds to {@code queryResultClass}
     * properties. If some properties of class are dynamically calculated values then
     * getters for this properties must have {@link ColumnExpression} annotation
     * specified. If column expression is an aggregate function
     * (with {@link ColumnExpression#isAggregate} set to true), then it is excluded
     * from {@code GROUP BY} clause.
     *
     * <p>It takes properties from {@code queryResultClass} and for each property
     * tries to find {@link EntityVariable} whose entity originally owns this property
     * and, when found, generates "{@code entityAlias.property}", where
     * {@code entityAlias} is received by calling {@code entityVariable.getAsAliasValue()}
     * method.
     * <p>If property from {@code queryResultClass} is annotated with
     * {@code ColumnExpression} annotation, then it generates
     * "{@code columnExpression}", where columnExpression is received from
     * {@code ColumnExpression} annotation value.
     *
     * <p>When two {@code EntityVariable} instances have same properties, preference
     * will be given to first found. So, to choose from which {@code EntityVariable}
     * instance property with equal name must be taken one can use insertion order
     * in {@code queryEntityVariables}. If two {@code EntityVariable} instances have
     * multiple properties with same names and for each such property
     * {@code EntityVariable} instance must be chosen in different preference than
     * such complex {@code GROUP BY} clause must be written manually.
     *
     * <p><b>Note: properties are always sorted in alphabetic order.</b>
     *
     * <p>Example usage:
     * <p>Let's say {@code queryResultClass} is a Spring Data JPA projection:
     * <pre><code>
     * public interface SomeProjection {
     *
     *     String getFieldOne();
     *
     *     String getFieldTwo();
     *
     *    {@literal @}ColumnExpression(value = "expression")
     *     String getFieldThree();
     *
     *    {@literal @}ColumnExpression(value = "aggregate", isAggregate = true)
     *     String getFieldFour();
     * }
     * </code></pre>
     *
     * and there are also two entities:
     *
     * <pre><code>
     *{@literal @}Entity
     * public class SomeEntityOne {
     *     private Object fieldOne;
     *     public Object getFieldOne() {
     *         return fieldOne;
     *     }
     * }
     *{@literal @}Entity
     * public class SomeEntityTwo {
     *     private Object fieldTwo;
     *     public Object getFieldTwo() {
     *         return fieldTwo;
     *     }
     * }
     * </code></pre>
     *
     * then:
     *
     * <pre>{@code
     * List<EntityVariable> queryEntityVariables = new ArrayList<>();
     * queryEntityVariables.add(new EntityVariable(SomeEntityOne.class, "entityOne"));
     * queryEntityVariables.add(new EntityVariable(SomeEntityTwo.class, "entityTwo"));
     *
     * String createdSelectClause = createGroupByClause(SomeProjection.class,
     *          queryEntityVariables);
     * }</pre>
     *
     * Value of {@code createdGroupByClause} would be: "{@code someEntityOne.fieldOne,
     * expression, someEntityOne.fieldTwo}"
     *
     * @param <T> the query result class type parameter
     * @param queryResultClass  the class that is used as return type of query
     * @param queryEntityVariables  the list that contains {@link EntityVariable}
     *                              instances of query
     *
     * @return the created {@code GROUP BY} clause string
     *
     * @throws IllegalArgumentException if property from {@code queryResultClass} cannot be found in
     *         any {@code EntityVariable} specified in {@code queryEntityVariables}
     */
    public static <T> String createGroupByClause(Class<T> queryResultClass, List<EntityVariable> queryEntityVariables) {
        SortedSet<Method> propertyGettersMethods = getPropertyGettersMethodsFromClass(queryResultClass,
                Comparator.comparing(Method::getName));

        StringJoiner sj = new StringJoiner(", ");
        for (Method propertyGetterMethod : propertyGettersMethods) {
            ColumnExpression ceAnnotation = propertyGetterMethod.getAnnotation(ColumnExpression.class);
            if (ceAnnotation == null || !ceAnnotation.isAggregate()) {
                if (ceAnnotation != null) {
                    sj.add(ceAnnotation.value());
                } else {
                    String propertyName = getPropertyName(propertyGetterMethod);
                    String baseGroupExpression = "%1$s.%2$s";
                    boolean propertyFound = false;
                    for (EntityVariable entityVariable : queryEntityVariables) {
                        if (entityVariable.getProperties().contains(propertyName)) {
                            propertyFound = true;
                            sj.add(format(baseGroupExpression, entityVariable.getAsAliasValue(), propertyName));
                        }
                    }
                    if (!propertyFound) {
                        String errMsg = format("Cannot find property %1$s from result class %2$s in any entity " +
                                "variable %3$s", propertyName, queryResultClass, queryEntityVariables);
                        throw new IllegalArgumentException(errMsg);
                    }
                }
            }
        }
        return sj.toString();
    }

    /**
     * Creates {@code ORDER BY} clause. It takes {@link Sort} instance and for each
     * {@link org.springframework.data.domain.Sort.Order} instance in it generates
     * "{@code property direction}" string, where {@code property} is received by
     * calling {@link org.springframework.data.domain.Sort.Order#getProperty()} method
     * and {@code direction} is received by calling
     * {@link org.springframework.data.domain.Sort.Order#getDirection()} method.
     *
     * @param queryResultClass  the class that is used as return type of query
     * @param sort  the {@code Sort} instance containing ordering information
     *
     * @return the created {@code ORDER BY} clause string
     *
     * @throws IllegalArgumentException if property specified in {@code Sort.Order}
     *         cannot be found in {@code queryResultClass}
     */
    public static String createOrderByClause(Sort sort, Class<?> queryResultClass) {
        StringJoiner orderClauseBuilder = new StringJoiner(" ");
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            Set<String> existingProperties = getPropertiesNames(queryResultClass);
            if (!existingProperties.stream().anyMatch(property::equals)) {
                String errMsg = format("Invalid property value for ordering, property %s is not exists in %s",
                        property, queryResultClass);
                throw new IllegalArgumentException(errMsg);
            }
            orderClauseBuilder.add(order.getProperty()).add(order.getDirection().toString());
        }
        return orderClauseBuilder.toString();
    }

    /**
     * Creates a map that contains all template variables for each entity. It takes
     * names of template variables by calling {@code EntityVariable} get methods
     * which end on "Key" and their values by calling a corresponding get methods
     * which end on "Value", and uses them as map entries values.
     *
     * @param queryEntityVariables  the list that contains {@link EntityVariable}
     *                              instances of query
     *
     * @return the map containing template variables of entity
     */
    public static Map<String, String> createTemplateVariablesForEntities(List<EntityVariable> queryEntityVariables) {
        Map<String, String> templateVariables = new HashMap<>();
        for (EntityVariable entityVariable : queryEntityVariables) {
            templateVariables.put(entityVariable.getAsSelfKey(), entityVariable.getAsSelfValue());
            templateVariables.put(entityVariable.getAsAliasKey(), entityVariable.getAsAliasValue());
            templateVariables.put(entityVariable.getAsPropKey(), entityVariable.getAsPropValue());
            templateVariables.put(entityVariable.getAsArgKey(), entityVariable.getAsArgValue());
        }
        return templateVariables;
    }

    /**
     * <p>Creates row mapper function object that creates an instance of
     * class specified in {@code queryResultClass} argument whose properties are
     * populated with values taken from query result row.
     *
     * <p><b>Note: properties are always sorted in alphabetic order. It is convenient
     * to use this method in conjunction with {@link #createSelectClause(Class, List)}
     * method. If {@code queryResultClass} is a DTO class then it must have a no arg
     * constructor.</b>
     *
     * @param queryResultClass  the class that is used as return type of query
     * @param <T> the query result class type parameter
     *
     * @return the row mapper function object
     */
    public static <T> Function<Object[], T> createRowMapper(Class<T> queryResultClass) {
        SortedSet<String> propertiesNames = getPropertiesNames(queryResultClass, Comparator.naturalOrder());
        if (queryResultClass.isInterface()) {
            return row -> {
                Map<String, Object> propertiesMap = convertRowToPropertiesMap(row, propertiesNames);
                return mapRowToProjection(propertiesMap, queryResultClass);
            };
        } else {
            try {
                Constructor<T> dtoNoArgConstructor = queryResultClass.getConstructor();
                SortedSet<Field> propertiesFieldsSet = getPropertiesFieldsSet(queryResultClass);
                return row -> mapRowToDTO(row, propertiesFieldsSet, dtoNoArgConstructor);
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                throw new RuntimeException("Cannot create row mapper: ", e);
            }
        }
    }

    private static <T> T mapRowToProjection(Map<String, Object> propertiesMap, Class<T> projectionClass) {
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        return projectionFactory.createProjection(projectionClass, propertiesMap);
    }

    private static Map<String, Object> convertRowToPropertiesMap(Object[] row, SortedSet<String> propertiesNames) {
        if (row.length != propertiesNames.size()) {
            String errMsg = format("Cannot convert row to properties map: row must have length(%s)" +
                    " equal to propertiesNames length(%s).", row.length, propertiesNames.size());
            throw new IllegalArgumentException(errMsg);
        }

        Map<String, Object> map = new HashMap<>();

        final int[] columnIdx = new int[1];
        propertiesNames.forEach(propertyName -> map.put(propertyName, row[columnIdx[0]++]));

        return map;
    }

    private static <T> T mapRowToDTO(Object[] row, SortedSet<Field> propertiesFieldsSet,
                                     Constructor<T> dtoNoArgConstructor) {
        if (row.length != propertiesFieldsSet.size()) {
            String errMsg = format("Cannot convert row to properties map: row must have length(%s)" +
                    " equal to propertiesNames length(%s).", row.length, propertiesFieldsSet.size());
            throw new IllegalArgumentException(errMsg);
        }

        try {
            T dtoInstance = dtoNoArgConstructor.newInstance();
            int columnIdx = 0;
            for (Field propertyField : propertiesFieldsSet) {
                propertyField.set(dtoInstance, row[columnIdx++]);
            }
            return dtoInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException  e) {
            throw new RuntimeException("Cannot map row to DTO: ", e);
        }
    }

    private static SortedSet<Field> getPropertiesFieldsSet(Class<?> dtoClass) throws NoSuchFieldException {
        Set<String> propertiesNames = getPropertiesNames(dtoClass);

        return Stream.of(dtoClass.getDeclaredFields())
                .filter(field -> propertiesNames.contains(field.getName()))
                .peek(propertyField -> propertyField.setAccessible(true))
                .collect(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(Field::getName))));
    }

}
