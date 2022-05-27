package com.bredogen.projectenv.extensions

import com.bredogen.projectenv.services.ProjectEnvService
import com.goide.execution.GoRunConfigurationBase
import com.goide.execution.GoRunningState
import com.goide.execution.extension.GoRunConfigurationExtension
import com.intellij.execution.configurations.RunnerSettings
import com.intellij.execution.target.TargetedCommandLineBuilder

class GoRunConfigurationExtension : GoRunConfigurationExtension() {
    override fun isApplicableFor(configuration: GoRunConfigurationBase<*>): Boolean = true
    override fun isEnabledFor(applicableConfiguration: GoRunConfigurationBase<*>, runnerSettings: RunnerSettings?): Boolean {
        return ProjectEnvService.getInstance(applicableConfiguration.getProject()).enableRunConfiguration
    }

    override fun patchCommandLine(configuration: GoRunConfigurationBase<*>, runnerSettings: RunnerSettings?, cmdLine: TargetedCommandLineBuilder, runnerId: String, state: GoRunningState<out GoRunConfigurationBase<*>>, commandLineType: GoRunningState.CommandLineType) {
        val envService = ProjectEnvService.getInstance(configuration.getProject())
        envService.getEnvValues().forEach {env -> cmdLine.addEnvironmentVariable(env.key, env.value)}
    }
}
