package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary

/**
 * Interface provides base functionality to translate individual tokens to specified format.
 *
 * @param T token representation type
 */
interface Vocabulary<T> {
    /**
     * Translate given token to vocabulary representation.
     *
     * @param tokenText token name
     * @return token representation in the vocabulary
     */
    fun translateToken(tokenText: String): T?

    /**
     * Translate given token from vocabulary representation to text.
     *
     * @param token token representation in the vocabulary
     * @return translated token
     */
    fun translateTokenBack(token: T): String?

    /**
     * Get current vocabulary size.
     *
     * @return vocabulary size
     */
    fun size(): Int
}