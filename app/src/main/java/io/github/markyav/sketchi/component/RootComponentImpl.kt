package io.github.markyav.sketchi.component

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import io.github.markyav.configuration.component.ConfigurationComponentImpl
import io.github.markyav.data.repository.SavedControlNetParamsRepository
import io.github.markyav.data.room.SavedControlNetParamsRepositoryImpl
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentImpl
import io.github.markyav.output.component.OutputComponent
import io.github.markyav.output.component.OutputComponentImpl
import io.github.markyav.sketchi.component.RootComponent.Child
import io.github.markyav.sketchi.component.RootComponent.Child.ConfigurationChild
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
    private val repository: SavedControlNetParamsRepository = SavedControlNetParamsRepositoryImpl.getSketchRepositoryImpl(applicationContext)
    private val configurationComponent = ConfigurationComponentImpl(
        componentContext = componentContext,
        repository = repository,
        onDrawScribbleClicked = { scribble -> navigation.push(Config.Drawing(scribble = scribble)) },
        onGenerateClicked = { params -> navigation.push(Config.Output(params = params)) },
        onSelectFromSavedClicked = { navigation.push(Config.Store) },
    )
    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Configuration,
        handleBackButton = true, // Automatically pop from the stack on back button presses
        childFactory = ::child,
    )
    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun child(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.Configuration -> ConfigurationChild(configurationComponent)
            is Config.Drawing -> DrawingChild(drawingComponent(componentContext))
            is Config.Output -> OutputChild(outputComponent(componentContext, config))
            is Config.Store -> StoreChild(storeComponent(componentContext))
        }

    private fun drawingComponent(componentContext: ComponentContext): DrawingComponent =
        DrawingComponentImpl(
            componentContext = componentContext,
            onFinishDrawing = { imageBitmap ->
                configurationComponent.updateScribble(imageBitmap)
                navigation.pop()
            },
            onBackClick = navigation::pop,
        )

    private fun outputComponent(componentContext: ComponentContext, config: Config.Output): OutputComponent =
        OutputComponentImpl(
            componentContext = componentContext,
            params = config.params,
            onBackClick = navigation::pop,
        )

    private fun storeComponent(componentContext: ComponentContext): StoreComponent =
        StoreComponentImpl(
            componentContext = componentContext,
            applicationContext = applicationContext,
            onSelect = { params ->
                configurationComponent.importControlNetParams(params)
                navigation.pop()
            },
            onBackClick = navigation::pop,
        )

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Configuration : Config
        @Serializable
        data class Drawing(val scribble: ImageBitmap?) : Config
        @Serializable
        data class Output(val params: ControlNetParams) : Config
        @Serializable
        data object Store : Config
    }
}