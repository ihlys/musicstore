package com.ihordev.core.repositories;

import java.lang.annotation.*;

/**
 * <p>Specifies getter method (property) in DTO class or Spring Data JPA projection
 * as dynamically calculated value. {@link QueryTemplateUtils} uses this
 * annotation when creating {@code SELECT} or {@code GROUP BY} clause and takes
 * it's value as column expression.
 *
 */

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ColumnExpression {

    /**
     * Specifies JPQL query fragment containing column expression for
     * dynamically calculated data.
     *
     * @return column expression string
     */

    String value();

    /**
     * Specifies if this column expression is aggregate function. Default is false.
     * If true, then {@link QueryTemplateUtils} will exclude this column
     * expression when creating {@code GROUP BY} clause.
     *
     * @return whether this column expression is an aggregate function or not
     */
    boolean isAggregate() default false;
}
