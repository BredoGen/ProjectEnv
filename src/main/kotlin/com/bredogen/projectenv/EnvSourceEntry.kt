package com.bredogen.projectenv

import com.bredogen.projectenv.providers.EnvProvider
import com.bredogen.projectenv.providers.EnvProviderFactory
import com.bredogen.projectenv.providers.files.DotEnvFileProvider
import com.bredogen.projectenv.providers.files.YamlFileProvider
import com.intellij.icons.AllIcons
import com.intellij.util.xmlb.annotations.Attribute
import com.intellij.util.xmlb.annotations.MapAnnotation
import com.intellij.util.xmlb.annotations.Tag
import javax.swing.Icon

@Tag("env-source-entry")
class EnvSourceEntry(
        @MapAnnotation(entryTagName = "params") val params: Map<String, String> = hashMapOf(),
        @Attribute("type") val type: EnvType? = null
) {
    enum class EnvType { ENV, JSON, YAML }

    companion object {
        fun getProviderFactory(type: EnvType): EnvProviderFactory =
                when (type) {
                    EnvType.ENV -> DotEnvFileProvider
                    EnvType.YAML -> YamlFileProvider
                    EnvType.JSON -> YamlFileProvider
                }

        val typeTitles: Map<EnvType, String> = mapOf(
                EnvType.ENV to ".env",
                EnvType.JSON to "json",
                EnvType.YAML to "yaml",
        )

        val typeIcons: Map<EnvType, Icon> = mapOf(
                EnvType.ENV to AllIcons.FileTypes.JsonSchema,
                EnvType.JSON to AllIcons.FileTypes.Json,
                EnvType.YAML to AllIcons.FileTypes.Yaml,
        )
    }

    val provider : EnvProvider
        get() = getProviderFactory(type!!).newInstance(params)

    val name : String
        get() = provider.name

}

