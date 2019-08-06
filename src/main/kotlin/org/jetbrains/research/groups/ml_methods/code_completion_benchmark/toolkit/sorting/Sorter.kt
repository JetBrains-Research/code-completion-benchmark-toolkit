package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.sorting

import com.intellij.codeInsight.completion.CompletionFinalSorter
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile

/**
 * Provides service to re-rank IntelliJ completions.
 *
 * To use ranking service, create the inheritor of [Sorter] and override [CompletionFinalSorter.Factory.newSorter].
 * Example:
 *        class MyCompletionSorterFactory : CompletionFinalSorter.Factory {
 *            override fun newSorter() = MyCompletionSorter()
 *        }
 * Also, you need to register this Factory as an application service extension in plugin.xml.
 */
abstract class Sorter : CompletionFinalSorter() {
    protected open fun getPsiElementByParameters(parameters: CompletionParameters): PsiElement {
        return parameters.originalPosition ?: return parameters.position
    }

    protected open fun getOpenFileByParameters(parameters: CompletionParameters): PsiFile {
        return parameters.originalFile
    }

    /**
     * Re-ranks completions list.
     *
     * @param completions completions suggested by IDE
     * @param parameters holds current completion information
     * @return re-ranked completions list
     */
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