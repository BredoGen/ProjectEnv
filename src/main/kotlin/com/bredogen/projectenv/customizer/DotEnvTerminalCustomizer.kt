package com.bredogen.projectenv.customizer

import com.bredogen.projectenv.core.EnvFileService
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.terminal.LocalTerminalCustomizer

class DotEnvTerminalCustomizer : LocalTerminalCustomizer() {
    override fun customizeCommandAndEnvironment(
        project: Project,
        command: Array<out String>,
        envs: MutableMap<String, String>
    ): Array<out String> {
        val envService = project.getService(EnvFileService::class.java)
        val fileEnvs = envService.collectEnv(HashMap(envs))
        envs.clear()
        envs.putAll(fileEnvs)
        return command
    }
}
