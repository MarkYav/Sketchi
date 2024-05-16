package io.github.markyav.sketchi.content

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import io.github.markyav.configuration.content.ConfigurationContent
import io.github.markyav.drawing.content.DrawingContent
import io.github.markyav.output.content.OutputContent
import io.github.markyav.sketchi.component.RootComponent
import io.github.markyav.store.content.StoreContent

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.ConfigurationChild -> ConfigurationContent(component = child.component)
            is RootComponent.Child.DrawingChild -> DrawingContent(component = child.component)
            is RootComponent.Child.OutputChild -> OutputContent(component = child.component)
            is RootComponent.Child.StoreChild -> StoreContent(component = child.component)
        }
    }
}