package com.bredogen.projectenv.core

import com.bredogen.projectenv.providers.DotEnvFileParser
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessProjectDir
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.VirtualFile
import java.io.File
import java.io.IOException

class EnvFileEntry(var path: String, var isEnabled: Boolean) {
    public val isEditable: Boolean = true

    val typeTitle: String
        get() {
            return ".env"
        }

    @Throws(IOException::class, EnvFileErrorException::class)
    fun process(project: Project, aggregatedEnv: Map<String, String>): Map<String, String> {
        val parser = DotEnvFileParser()
        if (isEnabled) {
            val file = getFile(project)
            return if (file == null || !parser.isFileLocationValid(file)) {
                aggregatedEnv
            } else {
                parser.process(file.path, aggregatedEnv)
            }
        }
        return aggregatedEnv
    }

    private fun getFile(project: Project): File? {
        var resolvedPath = path
        if (!FileUtil.isAbsolute(resolvedPath)) {
            val virtualFile: VirtualFile? = try {
                project.guessProjectDir()?.findFileByRelativePath(resolvedPath)
            } catch (ignored: AssertionError) { // can bee thrown deep from IoC implementation
                return null
            }
            if (virtualFile != null) {
                resolvedPath = virtualFile.path
            }
        }
        return File(resolvedPath)
    }
}
