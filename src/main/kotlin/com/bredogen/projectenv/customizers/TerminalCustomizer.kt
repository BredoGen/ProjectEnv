package com.bredogen.projectenv.customizers

import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.openapi.project.Project
import org.jetbrains.plugins.terminal.LocalTerminalCustomizer

class TerminalCustomizer : LocalTerminalCustomizer() {
    override fun customizeCommandAndEnvironment(project: Project, command: Array<out String>, envs: MutableMap<String, String>): Array<out String> {
        val envService = ProjectEnvService.getInstance(project)
        if (envService.enableTerminal) {
            envs.putAll(envService.getEnvValues())
        }
        return command
    }
}