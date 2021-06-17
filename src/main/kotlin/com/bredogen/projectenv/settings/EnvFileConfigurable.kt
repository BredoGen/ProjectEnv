package com.bredogen.projectenv.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

class EnvFileConfigurable(private val project: Project) : Configurable {
    private var settingsComponent: EnvFileSettingsComponent? = null

    override fun getDisplayName(): String {
        return "ProjectEnv"
    }

    override fun getPreferredFocusedComponent(): JComponent {
        return settingsComponent!!.getPreferredFocusedComponent()
    }

    override fun createComponent(): JComponent {
        settingsComponent = EnvFileSettingsComponent(project)
        return settingsComponent!!.getPanel()
    }

    override fun isModified(): Boolean {
        val settings = EnvFileSettings.getService(project)
        return settings.useEnvFile != settingsComponent!!.useEnvFile ||
            settings.overrideUserEnvs != settingsComponent!!.overrideUserEnvs ||
            settings.entries != settingsComponent!!.getEnvEntriesList()
    }

    override fun apply() {
        if (isModified) {
            val settings = EnvFileSettings.getService(project)
            settings.useEnvFile = settingsComponent!!.useEnvFile
            settings.overrideUserEnvs = settingsComponent!!.overrideUserEnvs
            settings.entries = settingsComponent!!.getEnvEntriesList()
        }
    }

    override fun reset() {
        val settings = EnvFileSettings.getService(project)
        settingsComponent!!.useEnvFile = settings.useEnvFile
        settingsComponent!!.overrideUserEnvs = settings.overrideUserEnvs
        settingsComponent!!.setEnvEntriesList(settings.entries!!)
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
