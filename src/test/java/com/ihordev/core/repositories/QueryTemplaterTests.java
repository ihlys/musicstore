package com.ihordev.core.repositories;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.ihordev.core.repositories.QueryTemplateUtils.*;

public class QueryTemplaterTests {

    interface TestProjection {

        String getFieldOne();

        String getFieldTwo();

        @ColumnExpression(value = "expression")
        String getFieldThree();

        @ColumnExpression(value = "aggregation", isAggregate = true)
        String getFieldFour();
    }

    static class TestDTO {

        private String fieldOne;
        private String fieldTwo;
        private String fieldThree;
        private String fieldFour;

        public TestDTO() {
        }

        public String getFieldOne() {
            return fieldOne;
        }

        public String getFieldTwo() {
            return fieldTwo;
        }

        @ColumnExpression(value = "expression")
        public String getFieldThree() {
            return fieldThree;
        }

        @ColumnExpression(value = "aggregation", isAggregate = true)
        public String getFieldFour() {
            return fieldFour;
        }
    }

    static class TestEntityOne {

        private String fieldOne;

        public String getFieldOne() {
            return fieldOne;
        }
    }

    static class TestEntityTwo {

        private String fieldTwo;

        public String getFieldTwo() {
            return fieldTwo;
        }
    }

    private List<EntityVariable> queryEntityVariablesList;

    @Before
    public void setUp() {
        queryEntityVariablesList = new ArrayList<>();
        queryEntityVariablesList.add(new EntityVariable(TestEntityOne.class, "entityOne"));
        queryEntityVariablesList.add(new EntityVariable(TestEntityTwo.class, "entityTwo"));
    }


    @Test
    public void shouldCreateSelectClauseForProjectionCorrectly() {
        String expectedSelectClause = "aggregation AS fieldFour, " +
                                      "testEntityOne.fieldOne AS fieldOne, " +
                                      "expression AS fieldThree, " +
                                      "testEntityTwo.fieldTwo AS fieldTwo";
        String actualSelectClause = createSelectClause(TestProjection.class, queryEntityVariablesList);

        Assert.assertEquals(expectedSelectClause, actualSelectClause);
    }

    @Test
    public void shouldCreateGroupByClauseCorrectly() {
        String expectedGroupByClause = "testEntityOne.fieldOne, " +
                                       "expression, " +
                                       "testEntityTwo.fieldTwo";

        String actualGroupByClause = createGroupByClause(TestProjection.class, queryEntityVariablesList);

        Assert.assertEquals(expectedGroupByClause, actualGroupByClause);
    }

    @Test
    public void shouldCreateOrderByClauseCorrectly() {
        String expectedOrderByClause = "fieldOne ASC fieldTwo DESC";
        Sort.Order orderOnFieldOne = new Sort.Order(Sort.Direction.ASC, "fieldOne");
        Sort.Order orderOnFieldTwo = new Sort.Order(Sort.Direction.DESC, "fieldTwo");
        Sort sort = new Sort(orderOnFieldOne, orderOnFieldTwo);

        String actualOrderByClause = createOrderByClause(sort, TestProjection.class);

        Assert.assertEquals(expectedOrderByClause, actualOrderByClause);
    }

    @Test
    public void shouldCreateTemplateVariablesMapCorrectly() {
        Map<String, String> expectedTemplateVariablesMap = new HashMap<>();
        expectedTemplateVariablesMap.put("entityOne", "TestEntityOne");
        expectedTemplateVariablesMap.put("entityOneAlias", "testEntityOne");
        expectedTemplateVariablesMap.put("entityOneAsProp", "testEntityOne");
        expectedTemplateVariablesMap.put("entityOneAsArg", "testEntityOne");
        expectedTemplateVariablesMap.put("entityTwo", "TestEntityTwo");
        expectedTemplateVariablesMap.put("entityTwoAlias", "testEntityTwo");
        expectedTemplateVariablesMap.put("entityTwoAsProp", "testEntityTwo");
        expectedTemplateVariablesMap.put("entityTwoAsArg", "testEntityTwo");

        Map<String, String> actualTemplateVariablesMap = createTemplateVariablesForEntities(queryEntityVariablesList);

        Assert.assertEquals(expectedTemplateVariablesMap, actualTemplateVariablesMap);
    }

    @Test
    public void shouldCreateRowMapperForProjectionCorrectly() {
        Object[] row = new Object[]{ "fieldFourValue", "fieldOneValue", "fieldThreeValue", "fieldTwoValue" };

        Function<Object[], TestProjection> actualRowMapper = createRowMapper(TestProjection.class);
        TestProjection actualProjection = actualRowMapper.apply(row);

        Assert.assertEquals(row[0], actualProjection.getFieldFour());
        Assert.assertEquals(row[1], actualProjection.getFieldOne());
        Assert.assertEquals(row[2], actualProjection.getFieldThree());
        Assert.assertEquals(row[3], actualProjection.getFieldTwo());
    }

    @Test
    public void shouldCreateRowMapperForDtoClassCorrectly() {
        Object[] row = new Object[]{ "fieldFourValue", "fieldOneValue", "fieldThreeValue", "fieldTwoValue" };

        Function<Object[], TestDTO> actualRowMapper = createRowMapper(TestDTO.class);
        TestDTO actualDto = actualRowMapper.apply(row);

        Assert.assertEquals(row[0], actualDto.getFieldFour());
        Assert.assertEquals(row[1], actualDto.getFieldOne());
        Assert.assertEquals(row[2], actualDto.getFieldThree());
        Assert.assertEquals(row[3], actualDto.getFieldTwo());
    }
}
