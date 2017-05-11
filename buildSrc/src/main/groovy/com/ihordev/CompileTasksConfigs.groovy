package com.ihordev

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.compile.JavaCompile
import org.yaml.snakeyaml.TypeDescription
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor


class CompileTasksConfigs {

    List<CompileTaskConfig> compileTasksConfigs

    List<Task> configureTasks(Project project) {
        // Checker Framework uses javax.annotation.processing.Messager under the hood.
        // To show compilation messages correctly, the property "line.separator"
        // must be overridden to value "\n". Otherwise, Messager's methods, like
        // printMessage(Diagnostic.Kind kind, CharSequence msg) would produce incorrect output
        // in Microsoft Windows OS.
        System.setProperty("line.separator", "\n")

        JavaCompile pluginCompileTask = project.tasks.withType(JavaCompile)[0]

        createCompileTasks(project, pluginCompileTask)
    }

    private List<Task> createCompileTasks(Project project, JavaCompile pluginCompileTask) {
        List<Task> compileTasks = []
        Task previousTask = null
        compileTasksConfigs.each {config ->
            Task currentTask = createCompileTask(config, project, pluginCompileTask)
            if (previousTask != null) {
                currentTask.dependsOn previousTask
            }
            previousTask = currentTask
            compileTasks << currentTask
        }

        return compileTasks
    }

    private Task createCompileTask(CompileTaskConfig compileTaskConfig, Project project, JavaCompile pluginCompileTask) {
        JavaCompile createdCompileTask = (JavaCompile) project.task(type : JavaCompile, compileTaskConfig.name)

        copyCompileTaskProperties(createdCompileTask, pluginCompileTask)

        def createdCompileTaskArgs = getPredefinedArgs(project)

        compileTaskConfig.compilerArgs.each {createdCompileTaskArgs << it.key + '=' + it.value }

        createdCompileTask.options.compilerArgs = createdCompileTaskArgs

        return createdCompileTask
    }

    private List<String> getPredefinedArgs(Project project) {
        [
            '-processor', 'org.checkerframework.checker.nullness.NullnessChecker',
            '-processorpath', "${project.configurations.checkerFramework.asPath}",
            "-Xbootclasspath/p:${project.configurations.checkerFrameworkAnnotatedJDK.asPath}"
        ]
    }

    private void copyCompileTaskProperties(JavaCompile targetTask, JavaCompile sourceTask) {
        targetTask.classpath = sourceTask.classpath
        targetTask.targetCompatibility = sourceTask.targetCompatibility
        targetTask.sourceCompatibility = sourceTask.sourceCompatibility
        targetTask.source = sourceTask.source
        targetTask.destinationDir = sourceTask.destinationDir
        targetTask.dependencyCacheDir = sourceTask.dependencyCacheDir
        targetTask.actions = sourceTask.actions
        targetTask.executer = sourceTask.executer
        targetTask.enabled = sourceTask.enabled
    }

    static CompileTasksConfigs loadFrom(String yamlFileName){
        Constructor constructor = new Constructor(CompileTasksConfigs)
        TypeDescription typeDescription = new TypeDescription(CompileTasksConfigs)
        typeDescription.putListPropertyType("compileTasksConfigs", CompileTaskConfig)
        constructor.addTypeDescription(typeDescription)
        Yaml yaml = new Yaml(constructor)

        InputStream yamlFileAsStream = CompileTasksConfigs.getClassLoader().getResourceAsStream(yamlFileName)
        return (CompileTasksConfigs) yaml.load(yamlFileAsStream)
    }

}
