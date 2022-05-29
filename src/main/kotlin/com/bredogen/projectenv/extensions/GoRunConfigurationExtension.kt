package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.goide.execution.GoRunConfigurationBase
import com.goide.execution.GoRunningState
import com.goide.execution.extension.GoRunConfigurationExtension
import com.goide.execution.testing.GoTestRunConfiguration
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.target.TargetedCommandLineBuilder

class GoRunConfigurationExtension : GoRunConfigurationExtension() {
    override fun isApplicableFor(configuration: GoRunConfigurationBase<*>): Boolean = true
    override fun isEnabledFor(applicableConfiguration: GoRunConfigurationBase<*>, runnerSettings: RunnerSettings?): Boolean {
        val projectEnvService = ProjectEnvService.getInstance(applicableConfiguration.getProject())

        if (!projectEnvService.includeTestConfiguration && isTestConfiguration(applicableConfiguration)) {
            return false
        }

        return projectEnvService.enableRunConfiguration
    }

    private fun isTestConfiguration(applicableConfiguration: RunConfigurationBase<*>): Boolean {
        return applicableConfiguration is GoTestRunConfiguration
    }

    override fun patchCommandLine(configuration: GoRunConfigurationBase<*>, runnerSettings: RunnerSettings?, cmdLine: TargetedCommandLineBuilder, runnerId: String, state: GoRunningState<out GoRunConfigurationBase<*>>, commandLineType: GoRunningState.CommandLineType) {
        val envService = ProjectEnvService.getInstance(configuration.getProject())
        envService.getEnvValues().forEach { env -> cmdLine.addEnvironmentVariable(env.key, env.value) }
    }
}
