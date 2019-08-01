package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans.Prediction
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary.Vocabulary

interface ModelRunner<T> {
    val id: String

    fun getCodeSuggestion(codePiece: Any): Prediction?
    fun getTopNCodeSuggestions(codePiece: Any, maxNumberOfSuggestions: Int = 10): List<Prediction>
}

abstract class AbstractModelRunner<T> : ModelRunner<T> {
    protected abstract val modelWrapper: ModelWrapper<T>
    protected open val vocabularyWrapper: Vocabulary<*>? = null
}