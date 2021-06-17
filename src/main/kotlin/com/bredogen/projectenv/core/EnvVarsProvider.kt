package com.bredogen.projectenv.core

import java.io.File
import java.io.IOException

interface EnvVarsProvider {
    @Throws(EnvFileErrorException::class, IOException::class)
    fun process(path: String, aggregatedEnv: Map<String, String>): Map<String, String>

    val isEditable: Boolean

    fun isFileLocationValid(file: File?): Boolean
}
