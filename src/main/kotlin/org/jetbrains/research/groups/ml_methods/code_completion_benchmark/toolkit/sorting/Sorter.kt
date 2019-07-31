package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.sorting

import com.intellij.codeInsight.completion.CompletionFinalSorter
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.lookup.LookupElement

import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.services.ModelRunnerRegistrar

abstract class Sorter : CompletionFinalSorter() {
    abstract val sorterID: String

    val modelService = ModelRunnerRegistrar.getInstance().registeredRunners.findLast { it.id == sorterID }

    protected abstract fun rankCompletions(
            completions: MutableIterable<LookupElement>,
            parameters: CompletionParameters
    ): MutableIterable<LookupElement>?

    override fun sort(items: MutableIterable<LookupElement>, parameters: CompletionParameters): MutableIterable<LookupElement> {
        return rankCompletions(items, parameters) ?: return items
    }
}