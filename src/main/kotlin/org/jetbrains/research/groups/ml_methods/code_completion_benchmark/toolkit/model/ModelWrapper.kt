package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import java.io.File

interface ModelWrapper<T> {
    fun loadModel(source: File? = null): ModelWrapper<T>?
    fun saveModel()
}

abstract class AbstractModelWrapper<T>: ModelWrapper<T> {
    protected abstract val model: T

    override fun loadModel(source: File?): ModelWrapper<T>? = null
    override fun saveModel() {}
}