package com.bredogen.projectenv.ui

import com.bredogen.projectenv.ui.actions.EnvRunConfigurationToggleAction
import com.bredogen.projectenv.ui.actions.EnvTerminalToggleAction
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ex.ToolWindowEx
import com.intellij.ui.content.ContentFactory

class WindowFactory : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowPanel = ToolWindowPanel(project)
        val content = ContentFactory.SERVICE.getInstance().createContent(toolWindowPanel.component, null, false)
        toolWindow.contentManager.addContent(content)

        if (toolWindow is ToolWindowEx) {
            toolWindow.setAdditionalGearActions(DefaultActionGroup(listOf(
                    EnvTerminalToggleAction(),
                    EnvRunConfigurationToggleAction()
            )))
        }
    }
}