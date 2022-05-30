package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.SdkAdditionalData
import com.jetbrains.python.console.PydevConsoleRunnerImpl
import com.jetbrains.python.run.PythonCommandLineEnvironmentProvider
import com.jetbrains.python.run.PythonRunParams

class PythonConsoleExtension : PythonCommandLineEnvironmentProvider {
    override fun extendEnvironment(
            project: Project,
            data: SdkAdditionalData?,
            cmdLine: GeneralCommandLine,
            runParams: PythonRunParams?
    ) {
        if (runParams !is PydevConsoleRunnerImpl.PythonConsoleRunParams) {
            return
        }

        val envService = ProjectEnvService.getInstance(project)
        if (envService.enableTerminal) {
            cmdLine.environment.putAll(envService.getEnvValues())
        }
    }
}