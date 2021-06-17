package com.github.bredogen.projectenv.services

import com.github.bredogen.projectenv.MyBundle
import com.intellij.openapi.project.Project

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
