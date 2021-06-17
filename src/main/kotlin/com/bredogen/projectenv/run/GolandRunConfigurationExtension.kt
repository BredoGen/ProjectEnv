package com.bredogen.projectenv.run

import com.bredogen.projectenv.core.EnvFileService
import com.goide.execution.GoRunConfigurationBase
import com.goide.execution.extension.GoRunConfigurationExtension
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunnerSettings

class GolandRunConfigurationExtension : GoRunConfigurationExtension() {
    override fun isApplicableFor(configuration: GoRunConfigurationBase<*>): Boolean {
        return true
    }

    override fun isEnabledFor(
        applicableConfiguration: GoRunConfigurationBase<*>,
        runnerSettings: RunnerSettings?
    ): Boolean {
        return true
    }

    override fun patchCommandLine(
        configuration: GoRunConfigurationBase<*>,
        runnerSettings: RunnerSettings?,
        cmdLine: GeneralCommandLine,
        runnerId: String
    ) {
        val currentEnv = cmdLine.environment
        val envService = configuration.project.getService(EnvFileService::class.java)
        val fileEnvs = envService.collectEnv(HashMap(currentEnv))
        currentEnv.clear()
        currentEnv.putAll(fileEnvs)
    }
}
