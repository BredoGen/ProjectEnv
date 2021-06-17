package com.bredogen.projectenv.run

import com.bredogen.projectenv.core.EnvFileService
import com.intellij.execution.RunConfigurationExtension
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.execution.configurations.RunnerSettings

class JavaRunConfigurationExtension : RunConfigurationExtension() {
    override fun isApplicableFor(configuration: RunConfigurationBase<*>): Boolean {
        return true
    }

    override fun <T : RunConfigurationBase<*>?> updateJavaParameters(
        configuration: T,
        params: JavaParameters,
        runnerSettings: RunnerSettings?
    ) {
        val envService = configuration?.project?.getService(EnvFileService::class.java)
        val fileEnvs = envService?.collectEnv(HashMap(params.env))

        if (fileEnvs != null) {
            params.env.clear()
            params.env.putAll(fileEnvs)
        }
    }
}
