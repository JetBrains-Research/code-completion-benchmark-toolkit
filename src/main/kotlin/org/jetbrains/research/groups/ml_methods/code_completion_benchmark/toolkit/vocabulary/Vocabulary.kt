package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.vocabulary

import java.io.File

interface Vocabulary<T> {
    fun translateCodePiece(codePiece: Any): Iterable<T>

    fun translateToken(name: String): T?
    fun translateTokenBack(token: T): String

    fun getVocabularySize(): Int

    //override if is necessary
    fun loadVocabulary(source: File): Vocabulary<T>? = null
    fun saveVocabulary() {}
}