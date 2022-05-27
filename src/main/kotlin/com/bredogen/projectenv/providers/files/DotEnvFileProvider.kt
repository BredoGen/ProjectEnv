package com.bredogen.projectenv.providers.files
import com.bredogen.projectenv.providers.EnvProviderFactory
import com.bredogen.projectenv.providers.EnvSourceException
import com.intellij.openapi.project.Project
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

class DotEnvFileProvider(private val params : Map<String, String>) : EnvFileProvider(params) {

    companion object : EnvProviderFactory {
        override fun newInstance(params : Map<String, String>): DotEnvFileProvider {
            return DotEnvFileProvider(params)
        }

        override fun createParams(project: Project): Map<String, String> {
            return EnvFileProvider.createParams(project)
        }
    }

    override fun getEnvValues(): LinkedHashMap<String, String> {

        val path = params["path"] ?: throw EnvSourceException("No valid path to env file")

        val result: LinkedHashMap<String, String> = linkedMapOf()
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
            throw EnvSourceException(ex)
        }
        return result
    }

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