# code-completion-benchmark-toolkit
IntelliJ code completion benchmarking toolkit.
## Installation
* Clone this repo to the root directory of your plugin.
* Add `include("code-completion-benchmark-toolkit")` line to your settings.gradle.kts file.
* Add `setPlugins(project(":code-completion-benchmark-toolkit"))` to `intellij` block in your build.gradle.kts file. See the example below.
```
intellij {
    pluginName = "your-plugin-name"
    version = "2019.1.3"
    setPlugins(project(":code-completion-benchmark-toolkit"))
}
```
* Add  `<depends>code-completion-benchmark-toolkit</depends>` line in your plugin.xml.
## Quick Start
### Deploying model
[Here] is an example of implementation of all components required to deploy a completion model.
#### Core components
* ***Model***\
  Your completion model. Since it does not require any interfaces implementation, it can be almost any type.
* ***Vocabulary (optional)***\
  Implement Vocabulary class, if your model uses vocabulary.
#### Wrappers
Use wrappers as an API for core components implementing ModelWrapper (AbstractModelWrapper)
and VocabularyWrapper (AbstractVocabularyWrapper) interfaces/classes.
#### Runners
Wrapped core components with some additional functionality are provided by classes implementing ModelRunner (AbstractModelRunner).
#### Providers
Every runner should be registered via related ModelRunnerProvider class in getModelRunners() function implementation.\
Example:
```
class ExampleModelRunnerProvider : ModelRunnerProvider {

    override fun getModelRunners(): Array<Class<*>> {
        return arrayOf(ExampleModelRunner::class.java)
    }
}
```
Then register this provider in plugin.xml by adding modelRunnerProvider extension.\
Example:
```
<extensions defaultExtensionNs="code-completion-benchmark-toolkit">
        <modelRunnerProvider implementation="your.fully.qualified.implementation.class.name.ExampleModelRunnerProvider"/>
</extensions>
```
### Ranking IDE completions
After your model is implemented and deployed, you can create a service to re-rank IntelliJ completion elements.

To sort IDE completions, create the inheritor of Sorter class and override Factory method (see the example below).\
Example:
```
class MyCompletionSorterFactory : CompletionFinalSorter.Factory {
    override fun newSorter() = MyCompletionSorter()
}

class MyCompletionSorter() : Sorter() { 
    ... 
}
```
Then register your Factory in plugin.xml.
```
<extensions defaultExtensionNs="com.intellij">
        <applicationService serviceInterface="com.intellij.codeInsight.completion.CompletionFinalSorter$Factory"
                            serviceImplementation="your.fully.qualified.implementation.class.name.MyCompletionSorterFactory"/>
    </extensions>
```