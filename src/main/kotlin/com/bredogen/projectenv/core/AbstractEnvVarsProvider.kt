package com.bredogen.projectenv.core

import java.io.File
import java.io.IOException

abstract class AbstractEnvVarsProvider : EnvVarsProvider {
    @Throws(EnvFileErrorException::class, IOException::class)
    protected abstract fun getEnvVars(path: String): Map<String, String>
    override val isEditable: Boolean
        get() = true

    @Throws(EnvFileErrorException::class, IOException::class)
    override fun process(path: String, aggregatedEnv: Map<String, String>): Map<String, String> {
        val result: MutableMap<String, String> = HashMap(aggregatedEnv)
        val overrides: Map<String, String> = getEnvVars(path)

        for ((key, value) in overrides) {
            result[key] = value
        }

        return result
    }

    override fun isFileLocationValid(file: File?): Boolean {
        return file != null && file.exists()
    }
}
