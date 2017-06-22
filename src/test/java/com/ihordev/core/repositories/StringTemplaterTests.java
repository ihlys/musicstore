package com.ihordev.core.repositories;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

public class StringTemplaterTests {

    @Test
    public void shouldResolveSimpleTemplateWithOneVariable() {
        String expectedResolvedString1 = "Template 'value1' text";
        String expectedResolvedString2 = "Template 'value1'";
        String expectedResolvedString3 = "'value1' text";

        String template1 = "Template [variable] text";
        String template2 = "Template [variable]";
        String template3 = "[variable] text";

        Map<String, String> variablesValuesMap = new HashMap<>();
        variablesValuesMap.put("variable", "'value1'");

        StringTemplater stringTemplater = new StringTemplater(variablesValuesMap);
        String actualResolvedString1 = stringTemplater.resolve(template1);
        String actualResolvedString2 = stringTemplater.resolve(template2);
        String actualResolvedString3 = stringTemplater.resolve(template3);

        Assert.assertEquals(expectedResolvedString1, actualResolvedString1);
        Assert.assertEquals(expectedResolvedString2, actualResolvedString2);
        Assert.assertEquals(expectedResolvedString3, actualResolvedString3);

        String expectedResolvedString4 = "Template  text";

        StringTemplater stringTemplater2 = new StringTemplater(new HashMap<>());
        String actualResolvedString4 = stringTemplater2.resolve(template1);
        Assert.assertEquals(expectedResolvedString4, actualResolvedString4);
    }

    @Test
    public void shouldResolveTemplateWithTwoVariables() {
        String expectedResolvedString = "Template 'value1' 'value2' text";

        String template = "Template [variable1] [variable2] text";

        Map<String, String> variablesValuesMap = new HashMap<>();
        variablesValuesMap.put("variable1", "'value1'");
        variablesValuesMap.put("variable2", "'value2'");

        StringTemplater stringTemplater = new StringTemplater(variablesValuesMap);
        String actualResolvedString = stringTemplater.resolve(template);

        Assert.assertEquals(expectedResolvedString, actualResolvedString);
    }

    @Test
    public void shouldResolveTemplateWithConditionalExpression() {
        String expectedResolvedString1 = "Template text 'value2' extra";
        String expectedResolvedString2 = "Template text 'value2' extra";
        String expectedResolvedString3 = "Template text 'value2' extra other text";

        String template1 = "Template text [variable1] ?[variable2] extra";
        String template2 = "Template text [variable1] ?[variable2] extra!";
        String template3 = "Template text [variable1] ?[variable2] extra! other text";

        Map<String, String> variablesValuesMap = new HashMap<>();
        variablesValuesMap.put("variable2", "'value2'");

        StringTemplater stringTemplater = new StringTemplater(variablesValuesMap);
        String actualResolvedString1 = stringTemplater.resolve(template1);
        String actualResolvedString2 = stringTemplater.resolve(template2);
        String actualResolvedString3 = stringTemplater.resolve(template3);

        Assert.assertEquals(expectedResolvedString1, actualResolvedString1);
        Assert.assertEquals(expectedResolvedString2, actualResolvedString2);
        Assert.assertEquals(expectedResolvedString3, actualResolvedString3);

        String expectedResolvedString4 = "Template text 'value1'";

        Map<String, String> variablesValuesMap2 = new HashMap<>();
        variablesValuesMap2.put("variable1", "'value1'");

        StringTemplater stringTemplater2 = new StringTemplater(variablesValuesMap2);
        String actualResolvedString4 = stringTemplater2.resolve(template1);

        Assert.assertEquals(expectedResolvedString4, actualResolvedString4);
    }

    @Test
    public void shouldResolveTemplateWithFullConditionalExpression() {
        String expectedResolvedString1 = "Template text  extra not exists";
        String expectedResolvedString2 = "Template text  extra not exists";
        String expectedResolvedString3 = "Template text  extra not exists other text";

        String template1 = "Template text [variable] ? [variable] extra exists : extra not exists";
        String template2 = "Template text [variable] ? [variable] extra exists : extra not exists!";
        String template3 = "Template text [variable] ? [variable] extra exists : extra not exists! other text";

        StringTemplater stringTemplater1 = new StringTemplater(new HashMap<>());
        String actualResolvedString1 = stringTemplater1.resolve(template1);
        String actualResolvedString2 = stringTemplater1.resolve(template2);
        String actualResolvedString3 = stringTemplater1.resolve(template3);

        Assert.assertEquals(expectedResolvedString1, actualResolvedString1);
        Assert.assertEquals(expectedResolvedString2, actualResolvedString2);
        Assert.assertEquals(expectedResolvedString3, actualResolvedString3);

        String expectedResolvedString4 = "Template text  'value1' extra exists ";

        Map<String, String> variablesValuesMap2 = new HashMap<>();
        variablesValuesMap2.put("variable", "'value1'");

        StringTemplater stringTemplater2 = new StringTemplater(variablesValuesMap2);
        String actualResolvedString4 = stringTemplater2.resolve(template1);

        Assert.assertEquals(expectedResolvedString4, actualResolvedString4);
    }

    @Test
    public void shouldResolveTemplateWithEscapedChars() {
        String expectedResolvedString = "Template text  'value' = :param extra exists ";

        String template = "Template text [variable] ? [variable] = \\:param extra exists : extra not exists";

        Map<String, String> variablesValuesMap = new HashMap<>();
        variablesValuesMap.put("variable", "'value'");

        StringTemplater stringTemplater = new StringTemplater(variablesValuesMap);
        String actualResolvedString = stringTemplater.resolve(template);

        Assert.assertEquals(expectedResolvedString, actualResolvedString);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowExceptionWhenVariableEndCharIsMissed_1() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 8: missed ] character.");

        String template1 = "[variable";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }

    @Test
    public void shouldThrowExceptionWhenVariableEndCharIsMissed_2() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 11: missed ] character.");

        String template1 = "[variable1 [variable2]";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }

    @Test
    public void shouldThrowExceptionWhenVariableEndCharIsMissed_3() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 11: missed ] character.");

        String template1 = "[variable1 ? [variable1] extra";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }

    @Test
    public void shouldThrowExceptionWhenVariableStartCharIsMissed() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 8: missed [ character.");

        String template1 = "variable]";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }

    @Test
    public void shouldThrowExceptionWhenVariableExistsCharIsMissed_1() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 34: missed ? character.");

        String template1 = "[variable] [variable] exists extra!";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }

    @Test
    public void shouldThrowExceptionWhenVariableExistsCharIsMissed_2() {
        thrown.expect(StringTemplaterException.class);
        thrown.expectMessage("Error at index 35: missed ? character.");

        String template1 = "[variable] [variable] exists extra : not exists extra";

        new StringTemplater(new HashMap<>()).resolve(template1);
    }
}
