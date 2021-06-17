package com.bredogen.projectenv.settings

import com.bredogen.projectenv.core.EnvFileEntry
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.ui.AnActionButtonUpdater
import com.intellij.ui.BooleanTableCellRenderer
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.TableView
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.ListTableModel
import java.awt.Dimension
import javax.swing.BoxLayout
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JPanel

@Suppress("MagicNumber")
class EnvFileSettingsComponent(private val project: Project) {
    private val mainPanel: JPanel

    private var envFilesModel: ListTableModel<EnvFileEntry>
    private var useEnvFileCheckBox: JCheckBox = JCheckBox("Enable Project Env File")
    private var overrideUserEnvCheckBox: JCheckBox = JCheckBox("Override all user defined env values")
    private var envFilesTable: TableView<EnvFileEntry>

    var useEnvFile: Boolean
        get() = useEnvFileCheckBox.isSelected
        set(value) {
            useEnvFileCheckBox.isSelected = value
        }

    var overrideUserEnvs: Boolean
        get() = overrideUserEnvCheckBox.isSelected
        set(value) {
            overrideUserEnvCheckBox.isSelected = value
        }

    fun getEnvEntriesList(): LinkedHashMap<String, Boolean> {
        val result = LinkedHashMap<String, Boolean>()
        for (entry in envFilesModel.items) {
            result[entry.path] = entry.isEnabled
        }
        return result
    }

    fun setEnvEntriesList(entries: Map<String, Boolean>) {
        val items = ArrayList<EnvFileEntry>()
        for ((path, enabled) in entries) {
            items.add(EnvFileEntry(path, enabled))
        }
        envFilesModel.items = items
    }

    fun getPanel(): JPanel {
        return mainPanel
    }

    fun getPreferredFocusedComponent(): JComponent {
        return useEnvFileCheckBox
    }

    private fun doAddAction(table: TableView<EnvFileEntry>, model: ListTableModel<EnvFileEntry>) {
        val chooserDescriptor = FileChooserDescriptorFactory
            .createSingleFileNoJarsDescriptor()
            .withTitle("Select Env File")
        val selectedFile = FileChooser.chooseFile(chooserDescriptor, project, null)
        if (selectedFile != null) {
            var selectedPath = selectedFile.path
            val baseDir: String = project.guessProjectDir()?.path ?: ""
            if (selectedPath.startsWith(baseDir)) {
                selectedPath = selectedPath.substring(baseDir.length + 1)
            }
            val newList = ArrayList(model.items)
            val newOptions = EnvFileEntry(
                selectedPath,
                true,
            )
            newList.add(newOptions)
            model.setItems(newList)
            val index = model.rowCount - 1
            model.fireTableRowsInserted(index, index)
            table.setRowSelectionInterval(index, index)
        }
    }

    init {
        val activeColumn: ColumnInfo<EnvFileEntry, Boolean> = EnvFileIsActiveColumnInfo()
        val fileColumn: ColumnInfo<EnvFileEntry, String> = EnvFilePathColumnInfo()
        envFilesModel = ListTableModel<EnvFileEntry>(activeColumn, fileColumn)

        val checkboxPanel = JPanel()
        checkboxPanel.layout = BoxLayout(checkboxPanel, BoxLayout.Y_AXIS)
        checkboxPanel.add(useEnvFileCheckBox)

        envFilesTable = TableView(envFilesModel)
        envFilesTable.emptyText.text = "No env variables files selected"

        val tableHeader = envFilesTable.tableHeader
        val fontMetrics = tableHeader.getFontMetrics(tableHeader.font)
        val preferredWidth = fontMetrics.stringWidth(activeColumn.name) + 20
        envFilesTable.columnModel.getColumn(0).cellRenderer = BooleanTableCellRenderer()

        val tableColumn = tableHeader.columnModel.getColumn(0)
        tableColumn.width = preferredWidth
        tableColumn.preferredWidth = preferredWidth
        tableColumn.minWidth = preferredWidth
        tableColumn.maxWidth = preferredWidth

        envFilesTable.columnSelectionAllowed = false
        envFilesTable.setShowGrid(false)
        envFilesTable.dragEnabled = false
        envFilesTable.showHorizontalLines = false
        envFilesTable.showVerticalLines = false
        envFilesTable.intercellSpacing = Dimension(0, 0)

        val envFilesTableDecorator = ToolbarDecorator.createDecorator(envFilesTable)
        val updater = AnActionButtonUpdater { useEnvFileCheckBox.isSelected }

        envFilesTableDecorator
            .setAddAction { doAddAction(envFilesTable, envFilesModel) }
            .setAddActionUpdater(updater)
            .setRemoveActionUpdater { e ->
                var allEditable = true
                for (entry in envFilesTable.selectedObjects) {
                    if (!entry.isEditable) {
                        allEditable = false
                        break
                    }
                }
                updater.isEnabled(e) && envFilesTable.selectedRowCount >= 1 && allEditable
            }

        val envFilesTableDecoratorPanel = envFilesTableDecorator.createPanel()
        val size = Dimension(-1, 150)
        envFilesTableDecoratorPanel.minimumSize = size
        envFilesTableDecoratorPanel.preferredSize = size

        useEnvFileCheckBox.addActionListener {
            envFilesTable.isEnabled = useEnvFileCheckBox.isSelected
        }

        val footerCheckboxPanel = JPanel()
        footerCheckboxPanel.layout = BoxLayout(footerCheckboxPanel, BoxLayout.Y_AXIS)
        footerCheckboxPanel.add(overrideUserEnvCheckBox)

        mainPanel = FormBuilder.createFormBuilder()
            .addComponent(checkboxPanel)
            .addComponent(envFilesTableDecoratorPanel)
            .addComponent(footerCheckboxPanel)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }
}
