package com.ihordev.core.util;

import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ihordev.core.util.ReflectionsUtils.getPropertiesNames;
import static java.lang.String.format;


public class JpaUtils {

    private JpaUtils() {
        throw new AssertionError("JpaUtils cannot be instantiated.");
    }

    public static Map<String, Object> convertRowToPropertiesMap(Object[] row, String... propertiesNames) {
        if (row.length != propertiesNames.length) {
            throw new IllegalArgumentException("Cannot convert row to properties map:" +
                    " row must have length equal to propertiesNames length.");
        }

        Map<String, Object> map = new HashMap<>();

        for (int i = 0; i < propertiesNames.length; i++) {
            map.put(propertiesNames[i], row[i]);
        }

        return map;
    }

    public static String getOrderByClauseFromSort(Sort sort, Class<?> sortedObjectsClass) {
        StringBuilder orderClauseBuilder = new StringBuilder();
        for (Sort.Order order : sort) {
            throwExceptionIfPropertyIsNotValid(order.getProperty(), sortedObjectsClass);
            orderClauseBuilder.append(order.getProperty()).append(" ").append(order.getDirection());
        }
        return orderClauseBuilder.toString();
    }

    public static void throwExceptionIfPropertyIsNotValid(String property, Class<?> propertyOwner) {
        List<String> existingProperties = getPropertiesNames(propertyOwner);
        if (!existingProperties.stream().anyMatch(property::equals)) {
            String errMsg = format("Invalid property value for ordering, property %s is not exists in %s",
                    property, propertyOwner);
            throw new IllegalArgumentException(errMsg);
        }
    }
}
