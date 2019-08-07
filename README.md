# code-completion-benchmark-toolkit
IntelliJ code completion benchmarking toolkit.
## Installation
1. Clone this repo to the root directory of your plugin.
2. Add `include("code-completion-benchmark-toolkit")` line to your settings.gradle.kts file.
3. Add `setPlugins(project(":code-completion-benchmark-toolkit"))` to `intellij` block in your build.gradle.kts file. See the example below.
```
intellij {
    pluginName = "your-plugin-name"
    version = "2019.1.3"
    setPlugins(project(":code-completion-benchmark-toolkit"))
}
```
## Quick Start
[Here](https://github.com/ml-in-programming/code-completion-benchmark-plugin/tree/master/src/main/kotlin/org/jetbrains/research/groups/ml_methods/code_completion_benchmark/ngram_completion) is an example of implementation of all components required to deploy your completion model.
### Core components
* ***Model***\
  Your completion model. Since it does not require any interfaces implementation, it can be nearly any type.
* ***Vocabulary (optional)***\
  Implement Vocabulary class, if your model uses vocabulary.
### Wrappers
Use wrappers as an API for core components implementing ModelWrapper (AbstractModelWrapper) and VocabularyWrapper (AbstractVocabularyWrapper) interfaces/classes.
### Runners
Wrapped core components with some additional functionality are provided by classes implementing ModelRunner (AbstractModelRunner).
### Providers
Every runner should be registered via related ModelRunnerProvider class in getModelRunners() function implementation.
Example:
```
class ExampleModelRunnerProvider : ModelRunnerProvider {

    override fun getModelRunners(): Array<Class<*>> {
        return arrayOf(ExampleModelRunner::class.java)
    }
}
```
Then register this provider in plugin.xml by adding modelRunnerProvider extension.
Example:
```
<extensions defaultExtensionNs="code-completion-benchmark-toolkit">
        <modelRunnerProvider implementation="your.full.qualified.implementation.class.name.ExampleModelRunnerProvider"/>
</extensions>
```
