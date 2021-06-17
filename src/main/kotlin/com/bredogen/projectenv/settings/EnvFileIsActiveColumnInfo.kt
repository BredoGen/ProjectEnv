@file:Suppress("ParameterListWrapping")
package com.bredogen.projectenv.settings

import com.bredogen.projectenv.core.EnvFileEntry
import com.intellij.ui.BooleanTableCellRenderer
import com.intellij.util.ui.ColumnInfo
import java.awt.Component
import javax.swing.JTable
import javax.swing.table.TableCellRenderer

class EnvFileIsActiveColumnInfo : ColumnInfo<EnvFileEntry, Boolean>("Enabled") {
    override fun valueOf(envFileEntry: EnvFileEntry): Boolean {
        return envFileEntry.isEnabled
    }

    override fun getColumnClass(): Class<*> {
        return Boolean::class.java
    }

    override fun setValue(element: EnvFileEntry, checked: Boolean) {
        element.isEnabled = checked
    }

    override fun isCellEditable(envFileEntry: EnvFileEntry): Boolean {
        return envFileEntry.isEditable
    }

    override fun getRenderer(envFileEntry: EnvFileEntry): TableCellRenderer {
        return object : BooleanTableCellRenderer() {
            override fun getTableCellRendererComponent(
                table: JTable,
                value: Any,
                isSelected: Boolean,
                hasFocus: Boolean,
                row: Int,
                column: Int
            ): Component {
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column)
            }
        }
    }
}
