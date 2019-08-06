package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import java.io.File
import java.nio.file.Path

/**
 * Interface provides several additional features to make completion model more convenient to use.
 * @param T model type
 */
//TODO: make externalizable/serializable?
interface ModelWrapper<T> {
    /**
     * Load model from file.
     *
     * @param source file from which the vocabulary will be loaded
     */
    fun loadModel(source: File? = null): T?

    /**
     * Load model from file.
     *
     * @param target file in which the vocabulary will be saved
     */
    fun saveModel(target: Path)
}

/**
 * This class wraps model object of type [T] and provides stub implementations for [loadModel] and [saveModel].
 *
 * @param T model type
 */
abstract class AbstractModelWrapper<T>: ModelWrapper<T> {
    /**
     * Completion model.
     */
    protected abstract val model: T

    override fun loadModel(source: File?): T? = null
    override fun saveModel(target: Path) {}
}