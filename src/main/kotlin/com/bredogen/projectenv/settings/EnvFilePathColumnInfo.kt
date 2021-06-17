package com.bredogen.projectenv.settings

import com.bredogen.projectenv.core.EnvFileEntry
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.ColumnInfo
import com.intellij.util.ui.UIUtil
import java.awt.Component
import javax.swing.DefaultCellEditor
import javax.swing.JTable
import javax.swing.table.DefaultTableCellRenderer
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer

class EnvFilePathColumnInfo : ColumnInfo<EnvFileEntry, String>("Path") {
    override fun isCellEditable(envFileEntry: EnvFileEntry): Boolean {
        return envFileEntry.isEditable && envFileEntry.isEnabled
    }

    override fun getEditor(envFileEntry: EnvFileEntry): TableCellEditor {
        return DefaultCellEditor(JBTextField())
    }

    override fun setValue(envFileEntry: EnvFileEntry, value: String?) {
        envFileEntry.path = value ?: ""
    }

    override fun valueOf(envFileEntry: EnvFileEntry): String {
        return envFileEntry.path
    }

    override fun getRenderer(entry: EnvFileEntry): TableCellRenderer {
        return object : DefaultTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable,
                value: Any,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                val renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
                border = null
                text = entry.path

                if (!entry.isEnabled) {
                    foreground = UIUtil.getLabelDisabledForeground()
                }

                return renderer
            }
        }
    }
}
