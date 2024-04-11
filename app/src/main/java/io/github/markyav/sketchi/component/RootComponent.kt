package io.github.markyav.sketchi.component

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.output.component.OutputComponent
import io.github.markyav.store.component.StoreComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    val drawingComponent: DrawingComponent
    val outputComponent: OutputComponent

    sealed class Child {
        object DrawingChild : Child()
        object OutputChild : Child()
        class StoreChild(val component: StoreComponent) : Child()
    }
}