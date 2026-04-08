package extension

import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

fun DependencyHandlerDelegate.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}