package io.github.markyav.sketchi.component

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import io.github.markyav.drawbox.controller.DrawBoxSubscription
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext,
    private val applicationContext: Context,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Drawing, // The initial child component is List
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::child,
    )
    override val childStack: Value<ChildStack<*, Child>> = stack
    override val showTwoPane: MutableStateFlow<Boolean> = MutableStateFlow(false)
    override val drawingComponent: DrawingComponent = DrawingComponentImpl(
        componentContext = componentContext,
        generate = { navigation.push(Config.Output) },
        select = { navigation.push(Config.Store) },
        applicationContext = applicationContext,
    )
    private val drawnBitmap = drawingComponent.drawController.getBitmap(512, DrawBoxSubscription.FinishDrawingUpdate)
    override val outputComponent: OutputComponent = OutputComponentImpl(
        componentContext = componentContext,
        drawnBitmap = drawnBitmap,
        isSideBySideMode = showTwoPane,
        applicationContext = applicationContext,
        onBackClick = navigation::pop,
    )

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Drawing -> DrawingChild
            is Config.Output -> OutputChild
            is Config.Store -> StoreChild(storeComponent(componentContext))
        }

    private fun storeComponent(componentContext: ComponentContext): StoreComponent =
        StoreComponentImpl(
            componentContext = componentContext,
            applicationContext = applicationContext,
            onSelect = { bitmap ->
                navigation.pop()
                drawingComponent.load(bitmap)
            },
            onBackClick = navigation::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        object Drawing : Config
        @Serializable
        object Output : Config
        @Serializable
        object Store : Config
    }
}