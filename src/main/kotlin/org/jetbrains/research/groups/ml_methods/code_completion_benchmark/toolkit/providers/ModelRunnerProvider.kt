package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.providers

import com.intellij.openapi.extensions.ExtensionPointName

interface ModelRunnerProvider {
    fun getModelRunners(): Array<Class<*>>

    companion object {
        val EP_NAME = ExtensionPointName.create<ModelRunnerProvider>(
            "code_completion_benchmark.completion.modelRunnerProvider"
        )
    }
}