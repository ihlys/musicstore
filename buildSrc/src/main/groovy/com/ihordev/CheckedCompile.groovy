package com.ihordev

import org.gradle.api.Project
import org.gradle.api.Task



class CheckedCompile {

    static Task getHookTask(Project project) {
        CompileTasksConfigs config = CompileTasksConfigs.loadFrom "compile-tasks-configs.yaml"
        def tasks = config.configureTasks(project)
        tasks.last()
    }

}
