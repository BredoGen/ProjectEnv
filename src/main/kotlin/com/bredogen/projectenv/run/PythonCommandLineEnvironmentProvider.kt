package com.bredogen.projectenv.run
import com.bredogen.projectenv.core.EnvFileService
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.projectRoots.SdkAdditionalData
import com.jetbrains.python.run.PythonCommandLineEnvironmentProvider
import com.jetbrains.python.run.PythonRunParams

class PythonCommandLineEnvironmentProvider : PythonCommandLineEnvironmentProvider {
    override fun extendEnvironment(
        project: Project,
        data: SdkAdditionalData?,
        cmdLine: GeneralCommandLine,
        runParams: PythonRunParams?
    ) {
        val envService = project.getService(EnvFileService::class.java)
        val currentEnv = cmdLine.environment
        val fileEnvs = envService.collectEnv(HashMap(currentEnv))
        currentEnv.clear()
        currentEnv.putAll(fileEnvs)
    }
}
