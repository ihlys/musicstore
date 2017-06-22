package com.ihordev.core.repositories;

import java.util.Map;

import static java.lang.String.format;

/**
 * <p>A class for generating strings by template.
 * <p>It substitutes variables in given string with values provided in map.
 * The syntax consists with special characters, which can be customized, but
 * default values are:
 * <ul>
 *     <li>variable start symbol = [</li>
 *     <li>variable end symbol = ]</li>
 *     <li>variable existence expression start symbol = ?</li>
 *     <li>variable existence expression not exists symbol = :</li>
 *     <li>variable existence expression end symbol = !</li>
 *     <li>escaping char = \\</li>
 * </ul>
 * <p>For example, simple variable syntax:
 *
 * <pre>{@code
 * String template = "some text [variable]";
 * Map<String, String> variablesValuesMap = new HashMap<>();
 * variablesValuesMap.put("variable", "value");
 * String resolvedString = StringTemplater.resolve(template, variablesValuesMap);
 * }</pre>
 *
 * Value of resolvedString would be: {@code some text value}.
 *
 * <p>Variable existence expression can be of two forms:
 *
 * <p>1. Short: if variable exists then it is substituted, otherwise template specified
 * between '?' and '!' symbols is evaluated.
 * <p>For example:
 *
 * <pre>{@code
 * String template = "some text [variableOne] ? template [variableTwo] if not exists!";
 * Map<String, String> variablesValuesMap1 = new HashMap<>();
 * variablesValuesMap1.put("variableOne", "valueOne");
 * Map<String, String> variablesValuesMap2 = new HashMap<>();
 * variablesValuesMap2.put("variableTwo", "valueTwo");
 * String resolvedString1 = StringTemplater.resolve(template, variablesValuesMap1);
 * String resolvedString2 = StringTemplater.resolve(template, variablesValuesMap2);
 * }</pre>
 *
 * Value of {@code resolvedString1} would be:
 * <tt>some text valueOne</tt>
 * <br>
 * Value of {@code resolvedString2} would be:
 * <tt>some text &nbsp;template valueTwo if not exists</tt>
 *
 * <p>2. Full: if variable exists then template specified between '?' and ':' symbols
 * is evaluated, otherwise template specified between ':' and '!' symbols is evaluated.
 * <p>For example:
 *
 * <pre>{@code
 * String template = "some text [variableOne] ? template [variableOne] if exists     " +
 *                   "                        : template [variableTwo] if not exists!";
 * Map<String, String> variablesValuesMap1 = new HashMap<>();
 * variablesValuesMap1.put("variableOne", "valueOne");
 * Map<String, String> variablesValuesMap2 = new HashMap<>();
 * variablesValuesMap2.put("variableTwo", "valueTwo");
 * String resolvedString1 = StringTemplater.resolve(template, variablesValuesMap1);
 * String resolvedString2 = StringTemplater.resolve(template, variablesValuesMap2);
 * }</pre>
 *
 * <p>Value of {@code resolvedString1} would be:
 * <tt>some text &nbsp;template valueOne if exists</tt>
 * <br>
 * Value of {@code resolvedString2} would be:
 * <tt>some text &nbsp;template valueTwo if not exists</tt>
 *
 * <p>Also, variable existence expression end symbol '!' can be omitted if expression is
 * placed in the end of the string template.
 *
 * <p>When string contains characters that {@code StringTemplater} uses in it's own
 * syntax, two solutions can be made:
 * <ol>
 *     <li>Customise {@code StringTemplater} syntax symbols;</li>
 *     <li>Use escape character, for example:
 *         "{@code WHERE entity.id = \\:entityId}".</li>
 * </ol>
 */

public class StringTemplater {

    private char varStartChar = '[';
    private char varEndChar = ']';
    private char varExistsStartChar = '?';
    private char varNotExistsChar = ':';
    private char varExistsEndChar = '!';

    private char escapeChar = '\\';

    private Map<String, String> variablesValuesMap;

    /**
     * Creates instance with all configuration values set by default and
     * {@code variablesValuesMap} set.
     *
     * @param variablesValuesMap  the map with {@code variable name -> value} entries.
     */
    public StringTemplater(Map<String, String> variablesValuesMap) {
        this.variablesValuesMap = variablesValuesMap;
    }

    /**
     * Creates instance with all configuration values specified and
     * {@code variablesValuesMap} set.
     *
     * @param varStartChar  the variable start symbol
     * @param varEndChar  the variable end symbol
     * @param varExistsStartChar  the variable existence expression start symbol
     * @param varNotExistsChar  the variable existence expression not exists symbol
     * @param varExistsEndChar  the variable existence expression end symbol
     * @param escapeChar  the escape symbol
     * @param variablesValuesMap  the map with {@code variable name -> value} entries.
     */
    public StringTemplater(char varStartChar, char varEndChar, char varExistsStartChar,
                           char varNotExistsChar, char varExistsEndChar, char escapeChar,
                           Map<String, String> variablesValuesMap) {
        this.varStartChar = varStartChar;
        this.varEndChar = varEndChar;
        this.varExistsStartChar = varExistsStartChar;
        this.varNotExistsChar = varNotExistsChar;
        this.varExistsEndChar = varExistsEndChar;
        this.escapeChar = escapeChar;
        this.variablesValuesMap = variablesValuesMap;
    }

    /**
     * Shorthand method for {@link #resolve(String)} when there is no need in configuring
     * instance of {@code StringTemplater} and template resolution must be done with
     * default configuration values.
     *
     * @param template  the string template
     * @param variablesValuesMap  the map with {@code variable name -> value} entries.
     * @return the string with substituted variable values
     *
     * @throws StringTemplaterException if template has errors in syntax
     */
    public static String resolve(String template, Map<String, String> variablesValuesMap) {
        return new StringTemplater(variablesValuesMap).resolve(template);
    }

    /**
     * Resolves given template and returns a string with substituted variables values.
     *
     * @param template  the string template
     * @return the string with substituted variable values
     *
     * @throws StringTemplaterException if template has errors in syntax
     */
    public String resolve(String template) {
        StringBuilder sb = new StringBuilder();
        String errMsgBase = "Error at index %s: ";

        int prevVarEndIdx = 0;

        int varStartIdx = -1;
        int varEndIdx = -1;
        int varExistsIdx = -1;
        int varNotExistsIdx = -1;

        for (int i = 0; i < template.length(); i++) {
            char curChar = template.charAt(i);

            if (curChar == escapeChar) {
                i++;
                continue;
            }

            boolean isVariableSubstituted = false;

            if (curChar == varStartChar) {
                if (varExistsIdx == -1) {
                    if (varStartIdx != -1) {
                        if (varEndIdx == -1) {
                            throw new StringTemplaterException(format(errMsgBase + "missed %s character.", i, varEndChar));
                        } else {
                            substituteVariable(template, varStartIdx, varEndIdx, prevVarEndIdx, sb);
                            isVariableSubstituted = true;
                            prevVarEndIdx = varEndIdx + 1;
                        }
                    }
                    varStartIdx = i;
                }
            } else if (curChar == varEndChar) {
                if (varExistsIdx == -1) {
                    if (varStartIdx != -1) {
                        varEndIdx = i;
                    } else {
                        throw new StringTemplaterException(format(errMsgBase + "missed %s character.", i, varStartChar));
                    }
                }
            } else if (curChar == varExistsStartChar) {
                if (varExistsIdx == -1) {
                    if (varEndIdx != -1) {
                        varExistsIdx = i;
                    } else {
                        throw new StringTemplaterException(format(errMsgBase + "missed %s character.", i, varEndChar));
                    }
                }
            } else if (curChar == varNotExistsChar) {
                if (varNotExistsIdx == -1) {
                    if (varExistsIdx != -1) {
                        varNotExistsIdx = i;
                    } else {
                        throw new StringTemplaterException(
                                format(errMsgBase + "missed %s character.", i, varExistsStartChar));
                    }
                }
            } else if (curChar == varExistsEndChar) {
                if (varExistsIdx == -1) {
                    throw new StringTemplaterException(format(errMsgBase + "missed %s character.", i, varExistsStartChar));
                } else {
                    resolveVarExpression(template, varStartIdx, varEndIdx, varExistsIdx,
                            varNotExistsIdx, prevVarEndIdx, sb, i);
                    isVariableSubstituted = true;
                    varStartIdx = -1;
                    prevVarEndIdx = i + 1;
                }
            } else if (varExistsIdx == -1 && varEndIdx != -1 && curChar != ' ') {
                substituteVariable(template, varStartIdx, varEndIdx, prevVarEndIdx, sb);
                isVariableSubstituted = true;
                varStartIdx = -1;
                prevVarEndIdx = varEndIdx + 1;
            }

            if (!isVariableSubstituted && i == template.length() - 1) {
                if (varStartIdx != -1) {
                    if (varEndIdx == -1) {
                        throw new StringTemplaterException(format(errMsgBase + "missed %s character.", i, varEndChar));
                    } else if (varExistsIdx != -1) {
                        resolveVarExpression(template, varStartIdx, varEndIdx, varExistsIdx, varNotExistsIdx,
                                prevVarEndIdx, sb, i + 1);
                    } else {
                        substituteVariable(template, varStartIdx, varEndIdx, prevVarEndIdx, sb);
                    }
                } else {
                    sb.append(unescape(template.substring(prevVarEndIdx, template.length())));
                }
            }

            if (isVariableSubstituted) {
                varEndIdx = -1;
                varExistsIdx = -1;
                varNotExistsIdx = -1;
            }
        }

        return sb.toString();
    }

    private void resolveVarExpression(String template, int varStartIdx, int varEndIdx, int varExistsIdx,
                                      int varNotExistsIdx, int prevVarEndIdx, StringBuilder sb,
                                      int varExpressionEndIdx) {
        sb.append(unescape(template.substring(prevVarEndIdx, varStartIdx)));
        String variableName = template.substring(varStartIdx + 1, varEndIdx);
        if (varNotExistsIdx != -1) {
            if (variablesValuesMap.containsKey(variableName)) {
                sb.append(resolve(template.substring(varExistsIdx + 1, varNotExistsIdx)));
            } else {
                sb.append(resolve(template.substring(varNotExistsIdx + 1, varExpressionEndIdx)));
            }
        } else {
            if (variablesValuesMap.containsKey(variableName)) {
                sb.append(variablesValuesMap.get(variableName));
            } else {
                sb.append(resolve(template.substring(varExistsIdx + 1, varExpressionEndIdx)));
            }
        }
    }

    private void substituteVariable(String template, int varStartIdx, int varEndIdx,
                                    int prevVarEndIdx, StringBuilder sb) {
        sb.append(unescape(template.substring(prevVarEndIdx, varStartIdx)));
        String variableName = template.substring(varStartIdx + 1, varEndIdx);
        if (variablesValuesMap.containsKey(variableName)) {
            sb.append(variablesValuesMap.get(variableName));
        }
    }

    private String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch != escapeChar) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
}
