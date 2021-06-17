package com.bredogen.projectenv.settings

import com.bredogen.projectenv.core.EnvFileEntry
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "com.bredogen.projectenv.settings.EnvFileSettings", storages = [Storage("env_files.xml")])
class EnvFileSettings : PersistentStateComponent<EnvFileSettings> {
    companion object {
        fun getService(project: Project): EnvFileSettings {
            return project.getService(EnvFileSettings::class.java)
        }
    }

    var useEnvFile: Boolean = false
    var overrideUserEnvs: Boolean = false
    var entries: LinkedHashMap<String, Boolean>? = null

    fun getEnvFiles(): ArrayList<EnvFileEntry> {
        val items = ArrayList<EnvFileEntry>()
        for ((path, enabled) in entries!!) {
            items.add(EnvFileEntry(path, enabled))
        }
        return items
    }

    override fun getState(): EnvFileSettings {
        return this
    }

    override fun loadState(state: EnvFileSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
