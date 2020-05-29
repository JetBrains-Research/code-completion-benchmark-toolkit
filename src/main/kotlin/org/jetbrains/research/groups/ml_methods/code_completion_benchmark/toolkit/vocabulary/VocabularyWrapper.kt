package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary

import java.io.File

/**
 * Interface provides functions to translate code fragments to suitable format for model querying.
 *
 * @param T token representation type
 * @param V code fragment representation type
 */
abstract class AbstractVocabularyWrapper<T, V> {
    /**
     * Model vocabulary.
     */
    protected abstract var vocabulary: Vocabulary<T>

    /**
     * Get vocabulary representation for given token.
     *
     * @param token token text
     * @return token representation in the vocabulary
     */
    open fun tokenTextToRepresentation(token: String): T? {
        return vocabulary.translateToken(token)
    }

    /**
     * Translate given token vocabulary representation to text.
     *
     * @param token token representation in the vocabulary
     * @return token text
     */
    open fun representationToTokenText(token: T): String {
        return vocabulary.translateTokenBack(token) ?: ""
    }

    /**
     * Get vocabulary representations for given list of tokens.
     *
     * @param tokens list of text representations of tokens
     * @return token representation in the vocabulary
     */
    open fun tokensListToRepresentations(tokens: List<String>): Iterable<T?> {
        return tokens.map { token -> tokenTextToRepresentation(token) }
    }

    /**
     * Get text for given list of token vocabulary representations.
     *
     * @param tokens token text
     * @return token representation in the vocabulary
     */
    open fun representationListToTokens(tokens: List<T>): Iterable<String?> {
        return tokens.map { token -> representationToTokenText(token) }
    }

    /**
     * Translate given code fragment to the specified format.
     *
     * @param codePiece code fragment to translate
     * @return suitable representation of code
     */
    abstract fun translateCodePiece(codePiece: Any): V?

    /**
     * Get current vocabulary size.
     *
     * @return vocabulary size
     */
    abstract fun getVocabularySize(): Int


    /**
     * Load vocabulary from file.
     *
     * @param source file from which the vocabulary will be loaded
     */
    abstract fun loadVocabulary(source: File): Vocabulary<T>

    /**
     * Save vocabulary to file.
     *
     * @param target file in which the vocabulary will be saved
     */
    open fun saveVocabulary(target: File) = Unit
}
