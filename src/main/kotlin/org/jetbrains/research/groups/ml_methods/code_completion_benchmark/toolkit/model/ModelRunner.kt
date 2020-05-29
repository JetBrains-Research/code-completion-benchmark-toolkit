package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import com.intellij.openapi.extensions.ExtensionPointName
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans.Prediction
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary.AbstractVocabularyWrapper

/**
 * Classes implementing this interface can be used to run [AbstractModelWrapper]-related functions over the code corpus.
 */
interface ModelRunner {
    /**
     * Get code suggestion with the highest probability.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @return code suggestion with the highest probability
     */

    fun getTopCodeSuggestion(codePiece: Any): Prediction?

    /**
     * Get stringified code suggestion with the highest probability.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @return code suggestion with the highest probability
     */
    fun getTopCodeSuggestionText(codePiece: Any): String?

    /**
     * Get N code suggestions with the highest probability.
     *
     * List should sorted by probability in decreasing order.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @param maxNumberOfSuggestions maximum number of completions
     * @return specified in [maxNumberOfSuggestions] number of stringified completions with the highest probability
     */
    fun getTopNCodeSuggestions(codePiece: Any, maxNumberOfSuggestions: Int = 10): List<Prediction>

    /**
     * Get N stringified code suggestions with the highest probability.
     *
     * List is sorted by probability in decreasing order.
     *
     * @param codePiece code fragment representation for which completion will be generated
     * @param maxNumberOfSuggestions maximum number of completions
     * @return specified in [maxNumberOfSuggestions] number of stringified completions with the highest probability
     */
    fun getTopNCodeSuggestionsText(codePiece: Any, maxNumberOfSuggestions: Int = 10): List<String>

    companion object {
        val EP_NAME = ExtensionPointName.create<ModelRunner>("completion.benchmark.toolkit.modelRunner")
    }
}

/**
 * This class provides default components as model and vocabulary for code processing and base [getTopNCodeSuggestionsText]
 * and [getTopCodeSuggestionText] implementations.
 */
abstract class AbstractModelRunner : ModelRunner {
    /**
     * Instance of [AbstractModelWrapper] that provides completion model used to generate code suggestions for this runner.
     */
    protected abstract val modelWrapper: AbstractModelWrapper<*>
    /**
     * Instance of class implementing [AbstractVocabularyWrapper] that is used to translate code fragments
     * (null, if model in [modelWrapper] does not use vocabulary).
     */
    protected open val vocabularyWrapper: AbstractVocabularyWrapper<*, *>? = null

    override fun getTopCodeSuggestionText(codePiece: Any): String {
        return getTopCodeSuggestion(codePiece).toString()
    }

    override fun getTopNCodeSuggestionsText(codePiece: Any, maxNumberOfSuggestions: Int): List<String> {
        return getTopNCodeSuggestions(codePiece, maxNumberOfSuggestions).map{ it.toString() }
    }
}
