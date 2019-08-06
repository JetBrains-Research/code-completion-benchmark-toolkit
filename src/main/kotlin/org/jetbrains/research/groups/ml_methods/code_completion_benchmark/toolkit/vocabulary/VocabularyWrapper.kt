package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary

import java.io.File

/**
 * Interface provides functions to translate code fragments to suitable format for model querying.
 *
 * @param T token representation type
 * @param V code fragment representation type
 */
//TODO: make externalizable/serializable?
interface VocabularyWrapper<T, V> {
    /**
     * Get vocabulary representation for given token.
     *
     * @param token token text
     * @return token representation in the vocabulary
     */
    fun tokenTextToRepresentation(token: String): T?

    /**
     * Translate given token vocabulary representation to text.
     *
     * @param token token representation in the vocabulary
     * @return token text
     */
    fun representationToTokenText(token: T): String?

    /**
     * Get vocabulary representations for given list of tokens.
     *
     * @param tokens list of text representations of tokens
     * @return token representation in the vocabulary
     */
    fun tokensListToRepresentations(tokens: List<String>): Iterable<T?>?

    /**
     * Get text for given list of token vocabulary representations.
     *
     * @param tokens token text
     * @return token representation in the vocabulary
     */
    fun representationListToTokens(tokens: List<T>): Iterable<String?>?

    /**
     * Translate given code fragment to the specified format.
     *
     * @param codePiece code fragment to translate
     * @return suitable representation of code
     */
    fun translateCodePiece(codePiece: Any): V?

    /**
     * Get current vocabulary size.
     *
     * @return vocabulary size
     */
    fun getVocabularySize(): Int

    /**
     * Load vocabulary from file.
     *
     * @param source file from which the vocabulary will be loaded
     */
    fun loadVocabulary(source: File): Vocabulary<T>?

    /**
     * Save vocabulary to file.
     *
     * @param target file in which the vocabulary will be saved
     */
    fun saveVocabulary(target: File)
}

/**
 * This class wraps vocabulary and provides base implementations of several functions.
 *
 * @param T model type
 */
abstract class AbstractVocabularyWrapper<T, V, K: Vocabulary<T>>: VocabularyWrapper<T, V> {
    /**
     * Model vocabulary.
     */
    protected abstract var vocabulary: K

    override fun tokenTextToRepresentation(token: String): T? {
        return vocabulary.translateToken(token)
    }

    override fun representationToTokenText(token: T): String {
        return vocabulary.translateTokenBack(token) ?: ""
    }

    override fun representationListToTokens(tokens: List<T>): Iterable<String?> {
        return tokens.map { token -> representationToTokenText(token) }
    }

    override fun tokensListToRepresentations(tokens: List<String>): Iterable<T?> {
        return tokens.map { token -> tokenTextToRepresentation(token) }
    }

    override fun loadVocabulary(source: File): K? = null
    override fun saveVocabulary(target: File) {}
}