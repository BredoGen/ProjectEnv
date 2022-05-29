package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.execution.JavaTestConfigurationBase
import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.jetbrains.python.testing.PyAbstractTestConfiguration
import com.jetbrains.python.testing.PyTestConfiguration
import com.jetbrains.python.testing.PythonTestConfigurationType

class JavaRunConfigurationExtension : RunConfigurationExtension() {
    override fun isApplicableFor(configuration: RunConfigurationBase<*>): Boolean = true

    override fun isEnabledFor(applicableConfiguration: RunConfigurationBase<*>, runnerSettings: RunnerSettings?): Boolean {
        val projectEnvService = ProjectEnvService.getInstance(applicableConfiguration.project)

        if (!projectEnvService.includeTestConfiguration && isTestConfiguration(applicableConfiguration)) {
            return false
        }

        return projectEnvService.enableRunConfiguration
    }

    private fun isTestConfiguration(applicableConfiguration: RunConfigurationBase<*>): Boolean {
        return applicableConfiguration is JavaTestConfigurationBase
    }

    override fun <T : RunConfigurationBase<*>> updateJavaParameters(configuration: T, params: JavaParameters, runnerSettings: RunnerSettings?) {
        val envService = ProjectEnvService.getInstance(configuration.project)
        params.env.putAll(envService.getEnvValues())
    }
}