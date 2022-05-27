package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunnerSettings
import com.jetbrains.python.run.AbstractPythonRunConfiguration
import com.jetbrains.python.run.PythonRunConfigurationExtension

class PythonRunConfigurationExtension : PythonRunConfigurationExtension() {
    override fun isApplicableFor(configuration: AbstractPythonRunConfiguration<*>): Boolean = true
    override fun isEnabledFor(applicableConfiguration: AbstractPythonRunConfiguration<*>, runnerSettings: RunnerSettings?): Boolean {
        return ProjectEnvService.getInstance(applicableConfiguration.project).enableRunConfiguration
    }

    override fun patchCommandLine(configuration: AbstractPythonRunConfiguration<*>, runnerSettings: RunnerSettings?, cmdLine: GeneralCommandLine, runnerId: String) {
        val envService = ProjectEnvService.getInstance(configuration.project)
        cmdLine.environment.putAll(envService.getEnvValues())
    }
}