package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.sorting

import com.intellij.codeInsight.completion.CompletionFinalSorter
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

abstract class Sorter : CompletionFinalSorter() {
    protected open fun getPsiElementByParameters(parameters: CompletionParameters): PsiElement {
        return parameters.originalPosition ?: return parameters.position
    }

    protected open fun getOpenFileByParameters(parameters: CompletionParameters): PsiFile {
        return parameters.originalFile
    }

    protected abstract fun rankCompletions(
            completions: MutableIterable<LookupElement>,
            parameters: CompletionParameters
    ): MutableIterable<LookupElement>?

    override fun sort(items: MutableIterable<LookupElement>, parameters: CompletionParameters): MutableIterable<LookupElement> {
        return rankCompletions(items, parameters) ?: return items
    }

    override fun getRelevanceObjects(elements: MutableIterable<LookupElement>): MutableMap<LookupElement, MutableList<Pair<String, Any>>> {
        return mutableMapOf()
    }
}