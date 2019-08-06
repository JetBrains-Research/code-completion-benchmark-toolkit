package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans.Prediction
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary.VocabularyWrapper

/**
 * Classes implementing this interface can be used to run [ModelWrapper]-related functions over the code corpus.
 */
interface ModelRunner {
    /**
     * Unique runner identifier.
     */
    val id: String
    /**
     * Get code suggestion with the highest probability.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @return code suggestion with the highest probability
     */
    fun getTopCodeSuggestion(codePiece: Any): Prediction?
    /**
     * Get N code suggestions with the highest probability.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @param maxNumberOfSuggestions maximum number of completions
     * @return specified in [maxNumberOfSuggestions] number of completions with the highest probability
     */
    fun getTopNCodeSuggestions(codePiece: Any, maxNumberOfSuggestions: Int = 10): List<Prediction>
}

/**
 * This class provides default components as model and vocabulary for code processing.
 * @property
 */
abstract class AbstractModelRunner : ModelRunner  {
    /**
     * Instance of [ModelWrapper] that provides completion model used to generate code suggestions for this runner.
     */
    protected abstract val modelWrapper: ModelWrapper<*>
    /**
     * Instance of class implementing [VocabularyWrapper] that is used to translate code fragments
     * (null, if model in [modelWrapper] does not use vocabulary).
     */
    protected open val vocabularyWrapper: VocabularyWrapper<*, *>? = null
}