package com.ihordev

class CompileTaskConfig {

    String name

    Map<String, String> compilerArgs

    @Override
    public String toString() {
        "CompileTask-\"" + name + "\":" + compilerArgs;
    }
}
