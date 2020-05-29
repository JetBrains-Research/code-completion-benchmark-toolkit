package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import java.io.File
import java.nio.file.Path

/**
 * This class wraps model object of type [T].
 *
 * @param T model type
 */
abstract class AbstractModelWrapper<T> {
    /**
     * Completion model.
     */
    protected abstract val model: T

    /**
     * Load model from file.
     *
     * @param source file from which the vocabulary will be loaded
     */
    open fun loadModel(source: File?): T? = null

    /**
     * Save model to target file.
     *
     * @param target file in which the vocabulary will be saved
     */
    open fun saveModel(target: Path) = Unit
}
