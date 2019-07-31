package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans

data class Prediction(
        val prediction: String,
        val probability: Double
)