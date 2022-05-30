package com.bredogen.projectenv.ui

import com.bredogen.projectenv.EnvSourceEntry
import com.bredogen.projectenv.services.ProjectEnvService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.ui.*
import com.intellij.ui.components.JBList
import java.awt.event.MouseEvent
import java.io.File
import javax.swing.JList
import javax.swing.event.ListDataEvent
import javax.swing.event.ListDataListener


class ToolWindowPanel(project: Project) : SimpleToolWindowPanel(true, true) {

    private val service: ProjectEnvService

    init {
        service = ProjectEnvService.getInstance(project)

        val listModel = CollectionListModel(service.envFiles)
        listModel.addListDataListener(object : ListDataListener {
            override fun intervalAdded(e: ListDataEvent?) {
                service.envFiles = listModel.toList()
            }

            override fun intervalRemoved(e: ListDataEvent?) {
                service.envFiles = listModel.toList()
            }

            override fun contentsChanged(e: ListDataEvent?) {
                service.envFiles = listModel.toList()
            }

        })

        val list = JBList(listModel)
        list.cellRenderer = object : ColoredListCellRenderer<Any>() {
            override fun customizeCellRenderer(list: JList<*>, value: Any?, index: Int, selected: Boolean, hasFocus: Boolean) {
                if (value is EnvSourceEntry) {
                    icon = EnvSourceEntry.typeIcons[value.type]
                    val projectPath = (project.guessProjectDir()?.path ?: "") + File.separator
                    val filePath = value.name.removePrefix(projectPath)

                    append(filePath)
                    append(" ${value.type}", SimpleTextAttributes.GRAY_SMALL_ATTRIBUTES)
                }
            }
        }
        object : DoubleClickListener() {
            override fun onDoubleClick(event: MouseEvent): Boolean {
                if (list.selectedValue == null) return true
                list.selectedValue.provider.handleDoubleClick(project)
                return true
            }
        }.installOn(list)

        val toolbarDecorator = ToolbarDecorator.createDecorator(list).apply {

            setAddAction { button ->
                val actionGroup = DefaultActionGroup()
                EnvSourceEntry.typeTitles.forEach {
                    (type, name) ->
                    actionGroup.add(object : AnAction(name, null, AllIcons.Actions.AddFile) {
                        override fun actionPerformed(e: AnActionEvent) {

                            val params = EnvSourceEntry.getProviderFactory(type).createParams(project)
                            if (params.isNotEmpty()) {
                                listModel.add(EnvSourceEntry(params, type))
                            }
                        }
                    })
                    actionGroup.addSeparator()
                }

                val popup: ListPopup = JBPopupFactory.getInstance().createActionGroupPopup(
                        "Add...",
                        actionGroup,
                        button.dataContext,
                        true,
                        null,
                        -1
                )
                popup.show(button.preferredPopupPoint)
            }
        }
        setContent(toolbarDecorator.createPanel())
    }
}