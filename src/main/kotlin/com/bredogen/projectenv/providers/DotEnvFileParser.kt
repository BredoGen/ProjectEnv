package com.bredogen.projectenv.providers

import com.bredogen.projectenv.core.AbstractEnvVarsProvider
import com.bredogen.projectenv.core.EnvFileErrorException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class DotEnvFileParser : AbstractEnvVarsProvider() {

    @Throws(EnvFileErrorException::class)
    override fun getEnvVars(path: String): Map<String, String> {
        val result: MutableMap<String, String> = LinkedHashMap()
        try {
            val lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)
            for (l in lines) {
                val strippedLine = l.trim { it <= ' ' }
                if (!strippedLine.startsWith("#") && strippedLine.contains("=")) {
                    val tokens = strippedLine.split("=".toRegex(), 2).toTypedArray()
                    val key = tokens[0]
                    val value = trim(tokens[1])
                    result[key] = value
                }
            }
        } catch (ex: IOException) {
            throw EnvFileErrorException(ex)
        }
        return result
    }

    companion object {
        private fun trim(value: String): String {
            val trimmed = value.trim { it <= ' ' }
            val doubleQuoted = trimmed.startsWith("\"") && trimmed.endsWith("\"")
            val singleQuoted = trimmed.startsWith("'") && trimmed.endsWith("'")
            return if (doubleQuoted || singleQuoted) {
                trimmed.substring(1, trimmed.length - 1)
            } else {
                trimmed.replace("\\s#.*$".toRegex(), "").replace("(\\s)\\\\#".toRegex(), "$1#").trim { it <= ' ' }
            }
        }
    }
}
