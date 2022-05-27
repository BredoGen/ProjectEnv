package com.bredogen.projectenv.providers.files

import com.bredogen.projectenv.providers.EnvProvider
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.OpenFileDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.OpenSourceUtil
import java.nio.file.Files
import java.nio.file.Paths


abstract class EnvFileProvider(private val params : Map<String, String>) : EnvProvider {

    override val name : String
        get() = params.getOrDefault("path", "")

    override val isFile : Boolean = true

    override val isValid : Boolean
        get() = isValidPath

    private val isValidPath : Boolean
        get() = Files.isReadable(Paths.get(params.getOrDefault("path", "")))

    private val path : String
        get() = params.getOrDefault("path", "")

    companion object {
        fun createParams(project: Project): Map<String, String> {
            val fileDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
            fileDescriptor.title = "Choose Env File"

            val file: VirtualFile? = FileChooser.chooseFile(fileDescriptor, project, null)
            if (file != null) {
                return hashMapOf("path" to file.path)
            }

            return hashMapOf()
        }
    }

    override fun handleDoubleClick(project: Project): Boolean {
        val file = VfsUtil.findFile(Paths.get(path), true)
        if (file != null) {
            OpenSourceUtil.navigate(OpenFileDescriptor(project, file))
        }
        return true
    }

}