package com.bredogen.projectenv.providers
import com.intellij.openapi.project.Project

interface EnvProvider {
    fun handleDoubleClick(project: Project) : Boolean
    fun getEnvValues() : LinkedHashMap<String, String>

    val isValid : Boolean
    val isFile : Boolean
    val name : String
}
