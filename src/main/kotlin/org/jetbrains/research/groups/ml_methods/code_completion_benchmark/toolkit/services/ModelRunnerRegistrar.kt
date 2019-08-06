package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.NotNullLazyValue
import com.intellij.util.ReflectionUtil

import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model.ModelRunner
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.providers.ModelRunnerProvider

import java.util.function.Supplier

/**
 * Service that instantiates model runners specified in [ModelRunnerProvider.getModelRunners] implementations.
 */
object ModelRunnerRegistrar {

    private val LOG = Logger.getInstance(
        "#org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.services.ModelRunnerRegistrar"
    )

    private val modelRunnerFactories = NotNullLazyValue.createValue {
        val providers = HashSet<ModelRunnerProvider>()
        providers.addAll(ModelRunnerProvider.EP_NAME.extensionList)

        val factories = ArrayList<Supplier<ModelRunner>>()
        registerModelRunners(providers, factories)

        factories
    }

    private val registeredRunners by lazy { createModelRunners() }

    fun getInstance(): ModelRunnerRegistrar {
        return ServiceManager.getService(ModelRunnerRegistrar::class.java)
    }

    /**
     * Use to get required runner by ID.
     */
    fun getRunnerById(id: String): ModelRunner? {
        return getInstance().registeredRunners.find { it.id == id }
    }

    /**
     * Use to access all available model runners.
     */
    fun getAvailableRunners(): List<ModelRunner> {
        return getInstance().registeredRunners
    }

    private fun <T> instantiateModelRunner(runnerClass: Class<T>): T? {
        return try {
            ReflectionUtil.newInstance(runnerClass)
        } catch (e: RuntimeException) {
            LOG.error(e.cause)
            null
        }
    }

    private fun registerModelRunners(
            runnerProviders: Collection<ModelRunnerProvider>,
            factories: MutableList<Supplier<ModelRunner>>
    ) {
        runnerProviders.forEach { provider ->
            provider.getModelRunners().forEach { runner ->
                factories.add(Supplier {
                   instantiateModelRunner(runner) as ModelRunner
                })
            }
        }
    }

    private fun createModelRunners(): List<ModelRunner> {
        val modelRunnerFactories = modelRunnerFactories.value
        val runners = ArrayList<ModelRunner>(modelRunnerFactories.size)

        if (runners.size > 1)
            throw RuntimeException()

        modelRunnerFactories.forEach { factory ->
            runners.add(factory.get())
        }
        return runners
    }
}