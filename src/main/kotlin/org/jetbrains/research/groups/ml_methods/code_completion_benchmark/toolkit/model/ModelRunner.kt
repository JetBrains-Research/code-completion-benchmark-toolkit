package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans.Prediction

interface ModelRunner<T> {
    val id: String

    val modelWrapper: ModelWrapper<T>

    fun getCodeSuggestion(codePiece: Any): Prediction?
    fun getTopNCodeSuggestions(codePiece: Any, maxNumberOfSuggestions: Int = 10): List<Prediction>
}