package io.github.markyav.sketchi.component

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentImpl
import io.github.markyav.output.component.OutputComponent
import io.github.markyav.output.component.OutputComponentImpl
import io.github.markyav.sketchi.component.RootComponent.Child
import io.github.markyav.sketchi.component.RootComponent.Child.DrawingChild
import io.github.markyav.sketchi.component.RootComponent.Child.OutputChild
import io.github.markyav.sketchi.component.RootComponent.Child.StoreChild
import io.github.markyav.store.component.StoreComponent
import io.github.markyav.store.component.StoreComponentImpl
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext,
    private val applicationContext: Context,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Drawing,
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::child,
    )
    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Drawing -> DrawingChild
            is Config.Output -> OutputChild
            is Config.Store -> StoreChild(storeComponent(componentContext))
        }

    override val drawingComponent: DrawingComponent = DrawingComponentImpl(
        componentContext = componentContext,
        onGenerateClicked = { navigation.push(Config.Output) },
        onSelectClicked = { navigation.push(Config.Store) },
    )

    override val outputComponent: OutputComponent = OutputComponentImpl(
        componentContext = componentContext,
        onBackClick = navigation::pop,
    )

    private fun storeComponent(componentContext: ComponentContext): StoreComponent =
        StoreComponentImpl(
            componentContext = componentContext,
            applicationContext = applicationContext,
            onSelect = { bitmap ->
                navigation.pop()
                //drawingComponent.load(bitmap) TODO: modify when chooser is implemented
            },
            onBackClick = navigation::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Drawing : Config
        @Serializable
        data object Output : Config
        @Serializable
        data object Store : Config
    }
}