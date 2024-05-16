package io.github.markyav.sketchi.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.github.markyav.configuration.component.ConfigurationComponent
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.output.component.OutputComponent
import io.github.markyav.store.component.StoreComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class ConfigurationChild(val component: ConfigurationComponent) : Child()
        data class DrawingChild(val component: DrawingComponent) : Child()
        data class OutputChild(val component: OutputComponent) : Child()
        data class StoreChild(val component: StoreComponent) : Child()
    }
}