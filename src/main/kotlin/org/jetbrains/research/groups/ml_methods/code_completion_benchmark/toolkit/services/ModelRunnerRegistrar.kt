package org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.services

import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.util.NotNullLazyValue
import org.jetbrains.research.groups.ml_methods.code_completion_benchmark.toolkit.model.ModelRunner
import org.slf4j.LoggerFactory
import java.util.function.Supplier

/**
 * Service that instantiates model runners specified in [ModelRunner] EPs implementations.
 */
object ModelRunnerRegistrar {

    private val LOG = LoggerFactory.getLogger(ModelRunnerRegistrar::class.java)

    private val modelRunnerFactories = NotNullLazyValue.createValue {
        val providers = HashSet<ModelRunner>()
        providers.addAll(ModelRunner.EP_NAME.extensionList)
        val factories = ArrayList<Supplier<ModelRunner>>()
        registerModelRunners(providers, factories)

        factories
    }

    private val registeredRunners by lazy { createModelRunners() }

    fun getInstance(): ModelRunnerRegistrar {
        return ServiceManager.getService(ModelRunnerRegistrar::class.java)
    }

    /**
     * Use to access all available model runners.
     */
    fun getCurrentRunner(): ModelRunner {
        require(registeredRunners.size <= 1) { "Only one model runner should be registered" }
        return registeredRunners.single()
    }

    private fun <T> instantiateModelRunner(runnerClass: Class<T>): T? {
        return try {
            runnerClass.newInstance()
        } catch (e: RuntimeException) {
            LOG.error(e.message)
            null
        }
    }

    private fun registerModelRunners(
            runners: Collection<ModelRunner>,
            factories: MutableList<Supplier<ModelRunner>>
    ) {
        for (runner in runners)
            factories.add(Supplier {
                instantiateModelRunner(runner.javaClass) as ModelRunner
            })
    }

    private fun createModelRunners(): List<ModelRunner> {
        val runners = ArrayList<ModelRunner>(modelRunnerFactories.value.size)

        if (runners.size > 1) error("Only one model runner should be registered")

        for (factory in modelRunnerFactories.value) {
            runners.add(factory.get())
        }

        return runners
    }
}
