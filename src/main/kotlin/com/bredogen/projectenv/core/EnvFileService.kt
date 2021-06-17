package com.bredogen.projectenv.core

import com.bredogen.projectenv.settings.EnvFileSettings
import com.intellij.execution.ExecutionException
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.io.IOException

@Service
class EnvFileService(project: Project) {
    private val myProject: Project = project

    @Throws(ExecutionException::class)
    fun collectEnv(runConfigEnv: Map<String, String>): Map<String, String> {
        val settings = EnvFileSettings.getService(myProject)

        if (settings.useEnvFile) {
            var result: Map<String, String> = HashMap()
            for (entry in settings.getEnvFiles()) {
                result = try {
                    entry.process(myProject, result)
                } catch (e: EnvFileErrorException) {
                    throw ExecutionException(e)
                } catch (e: IOException) {
                    throw ExecutionException(e)
                }
            }

            return if (settings.overrideUserEnvs) {
                runConfigEnv + result
            } else {
                result + runConfigEnv
            }
        } else {
            return runConfigEnv
        }
    }
}
