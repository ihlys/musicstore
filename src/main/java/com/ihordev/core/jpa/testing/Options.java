package com.ihordev.core.jpa.testing;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class Options {

    private Map<String, Object> optionsMap = new HashMap<>();

    public Options(Map<String, Object> optionsMap) {
        this.optionsMap = optionsMap;
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(String key, T defaultValue) {
        if (optionsMap.containsKey(key)) {
            return (T) optionsMap.get(key);
        } else {
            return defaultValue;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) optionsMap.get(key);
    }

    public boolean hasOption(String key) {
        return optionsMap.containsKey(key);
    }

    public Options add(Option... options) {
        Stream.of(options).forEach(option -> optionsMap.put(option.key, option.value));
        return this;
    }

    public static Options options(Option... options) {
        Map<String, Object> optionsMap = new HashMap<>();
        Stream.of(options).forEach(option -> optionsMap.put(option.key, option.value));
        return new Options(optionsMap);
    }

    public static Options noOptions() {
        return new Options(new HashMap<>());
    }

    public static class Option {

        private String key;
        private Object value;

        public Option(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public static Option op(String key, Object value) {
            return new Option(key, value);
        }
    }

}
