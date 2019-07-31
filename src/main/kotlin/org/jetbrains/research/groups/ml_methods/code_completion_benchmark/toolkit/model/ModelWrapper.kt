package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model

import java.io.File

interface ModelWrapper<T> {
    val model: T

    //override if necessary
    fun loadModel(source: File? = null): ModelWrapper<T>? = null
    fun saveModel() {}
}