package com.bredogen.projectenv.services

import com.bredogen.projectenv.EnvSourceEntry
import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "ProjectEnvService", storages = [Storage("project_env.xml")])
class ProjectEnvService : PersistentStateComponent<ProjectEnvService>, BaseState() {
    companion object {
        @JvmStatic
        fun getInstance(project: Project): ProjectEnvService = project.service()
    }
    var envFiles by list<EnvSourceEntry>()

    var enableTerminal by property(true)
    var enableRunConfiguration by property(true)

    override fun getState(): ProjectEnvService {
        return this
    }

    override fun loadState(state: ProjectEnvService) {
        XmlSerializerUtil.copyBean(state, this)
    }

    fun getEnvValues(): LinkedHashMap<String, String> {
        val result = linkedMapOf<String, String>()

        envFiles.forEach { envFile ->
            if (envFile.provider.isValid) {
                result.putAll(envFile.provider.getEnvValues())
            }
        }

        return result
    }
}
