package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.providers

import com.intellij.openapi.extensions.ExtensionPointName
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model.ModelRunner
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.services.ModelRunnerRegistrar

/**
 * Every extension that implements this interface will be automatically
 * queried for model runner classes by [ModelRunnerRegistrar].
 */
interface ModelRunnerProvider {
    /**
     * Query method for model runners provided by a plugin.
     * @return classes that extend [ModelRunner]
     */
    fun getModelRunners(): Array<Class<*>>

    companion object {
        val EP_NAME = ExtensionPointName.create<ModelRunnerProvider>("code-completion-benchmark-toolkit.modelRunnerProvider")
    }
}