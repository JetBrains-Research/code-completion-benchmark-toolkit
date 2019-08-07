package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.beans

/**
 * Representation of code suggestion element.
 *
 * @property prediction contains prediction text
 * @property probability contains the probability of prediction
 */
data class Prediction(
        val prediction: String,
        val probability: Double
) {
    override fun toString(): String {
        return prediction
    }
}