package com.bredogen.projectenv.providers

import com.intellij.openapi.project.Project

interface EnvProviderFactory {
    fun newInstance(params : Map<String, String>): EnvProvider
    fun createParams(project: Project) : Map<String, String>
}