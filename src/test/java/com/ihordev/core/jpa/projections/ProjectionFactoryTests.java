package com.ihordev.core.jpa.projections;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;


public class ProjectionFactoryTests {

    interface ErrorProjection {

        void notPropertyMethod();
    }

    interface Projection {

        String getStringProperty();

        int getIntegerProperty();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionIfProjectionInterfaceHasNoProperties() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Cannot create projection factory for interface without properties.");

        ProjectionFactory<ErrorProjection> projectionFactory = new ProjectionFactory<>(ErrorProjection.class);
    }

    @Test
    public void shouldThrowExceptionIfNotAllPropertiesInProjectionAreSet() {
        thrown.expect(IllegalArgumentException.class);
        Set<String> propertiesThatAreNotSet = new HashSet<>();
        propertiesThatAreNotSet.add("integerProperty");
        String errMsg = format("Cannot create projection: properties %s are not set.", propertiesThatAreNotSet);
        thrown.expectMessage(errMsg);

        String stringPropertyValue = "string property value";

        ProjectionFactory<Projection> projectionFactory = new ProjectionFactory<>(Projection.class);
        Map<String, Object> projectionsProperties = new HashMap<>();
        projectionsProperties.put("stringProperty", stringPropertyValue);
        Projection projection = projectionFactory.createProjection(projectionsProperties);
    }

    @Test
    public void shouldCreateProjectionCorrectly() {
        String stringPropertyValue = "string property value";
        int integerPropertyValue = 10;

        ProjectionFactory<Projection> projectionFactory = new ProjectionFactory<>(Projection.class);
        Map<String, Object> projectionsProperties = new HashMap<>();
        projectionsProperties.put("stringProperty", stringPropertyValue);
        projectionsProperties.put("integerProperty", integerPropertyValue);
        Projection projection = projectionFactory.createProjection(projectionsProperties);

        Assert.assertEquals(stringPropertyValue, projection.getStringProperty());
        Assert.assertEquals(integerPropertyValue, projection.getIntegerProperty());
    }


}
