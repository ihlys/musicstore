package com.ihordev.core.repositories;

import com.ihordev.core.util.ReflectionsUtils;

import java.util.Set;

import static com.ihordev.core.util.StringUtils.decapitalize;

/**
 * A class that contains various entity references in JPQL query
 * depending on context.
 * <p>Entity references:
 * <ul>
 *     <li>As itself;</li>
 *     <li>As alias;</li>
 *     <li>As property;</li>
 *     <li>As argument.</li>
 * </ul>
 * <p>It also contains properties names of entity that can be referenced
 * in JPQL query.
 */

public class EntityVariable {

    private String asSelfKey;
    private String asAliasKey;
    private String asPropKey;
    private String asArgKey;

    private String asSelfValue;
    private String asAliasValue;
    private String asPropValue;
    private String asArgValue;

    private Set<String> properties;

    public EntityVariable(Class<?> entityClass, String baseNameInTemplate) {
        this.asSelfKey = baseNameInTemplate;
        this.asAliasKey = baseNameInTemplate + "Alias";
        this.asPropKey = baseNameInTemplate + "AsProp";
        this.asArgKey = baseNameInTemplate + "AsArg";

        String entityName = entityClass.getSimpleName();
        this.asSelfValue = entityName;
        this.asAliasValue = decapitalize(entityName);
        this.asPropValue = decapitalize(entityName);
        this.asArgValue = decapitalize(entityName);

        this.properties = ReflectionsUtils.getPropertiesNames(entityClass);
    }

    public String getAsSelfValue() {
        return asSelfValue;
    }

    public EntityVariable overrideAsSelf(String asSelfValue) {
        this.asSelfValue = asSelfValue;
        return this;
    }

    public EntityVariable appendToAsSelf(String value) {
        this.asSelfValue = this.asSelfValue + value;
        return this;
    }

    public String getAsAliasValue() {
        return asAliasValue;
    }

    public EntityVariable overrideAsAlias(String alias) {
        this.asAliasValue = alias;
        return this;
    }

    public EntityVariable appendToAsAlias(String value) {
        this.asAliasValue = this.asAliasValue + value;
        return this;
    }

    public String getAsPropValue() {
        return asPropValue;
    }

    public EntityVariable overrideAsProp(String asProp) {
        this.asPropValue = asProp;
        return this;
    }

    public EntityVariable appendToAsProp(String value) {
        this.asPropValue = this.asPropValue + value;
        return this;
    }

    public String getAsArgValue() {
        return asArgValue;
    }

    public EntityVariable overrideAsArg(String asArg) {
        this.asArgValue = asArg;
        return this;
    }

    public EntityVariable appendToAsArg(String value) {
        this.asArgValue = this.asArgValue + value;
        return this;
    }

    public String getAsSelfKey() {
        return asSelfKey;
    }

    public String getAsAliasKey() {
        return asAliasKey;
    }

    public String getAsPropKey() {
        return asPropKey;
    }

    public String getAsArgKey() {
        return asArgKey;
    }

    public Set<String> getProperties() {
        return properties;
    }

    @Override
    public String toString() {
        return asSelfValue;
    }
}
