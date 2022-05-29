package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.jetbrains.python.run.AbstractPythonRunConfiguration
import com.jetbrains.python.run.PythonRunConfigurationExtension
import com.jetbrains.python.testing.PyAbstractTestConfiguration

class PythonRunConfigurationExtension : PythonRunConfigurationExtension() {
    override fun isApplicableFor(configuration: AbstractPythonRunConfiguration<*>): Boolean = true
    override fun isEnabledFor(applicableConfiguration: AbstractPythonRunConfiguration<*>, runnerSettings: RunnerSettings?): Boolean {
        val projectEnvService = ProjectEnvService.getInstance(applicableConfiguration.project)

        if (!projectEnvService.includeTestConfiguration && isTestConfiguration(applicableConfiguration)) {
            return false
        }

        return projectEnvService.enableRunConfiguration
    }

    private fun isTestConfiguration(applicableConfiguration: RunConfigurationBase<*>): Boolean {
        return applicableConfiguration is PyAbstractTestConfiguration
    }

    override fun patchCommandLine(configuration: AbstractPythonRunConfiguration<*>, runnerSettings: RunnerSettings?, cmdLine: GeneralCommandLine, runnerId: String) {
        val envService = ProjectEnvService.getInstance(configuration.project)
        cmdLine.environment.putAll(envService.getEnvValues())
    }
}