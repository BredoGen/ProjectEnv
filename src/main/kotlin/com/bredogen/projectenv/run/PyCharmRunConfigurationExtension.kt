package com.bredogen.projectenv.run

import com.bredogen.projectenv.core.EnvFileService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.RunnerSettings
import com.jetbrains.python.run.AbstractPythonRunConfiguration
import com.jetbrains.python.run.PythonRunConfigurationExtension

class PyCharmRunConfigurationExtension : PythonRunConfigurationExtension() {
    override fun isApplicableFor(configuration: AbstractPythonRunConfiguration<*>): Boolean {
        return true
    }

    override fun isEnabledFor(
        applicableConfiguration: AbstractPythonRunConfiguration<*>,
        runnerSettings: RunnerSettings?
    ): Boolean {
        return true
    }

    override fun patchCommandLine(
        configuration: AbstractPythonRunConfiguration<*>,
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
