package io.github.markyav.sketchi.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import io.github.markyav.drawing.content.DrawingContent
import io.github.markyav.output.content.OutputContent
import io.github.markyav.sketchi.component.RootComponent
import io.github.markyav.store.content.StoreContent

@Composable
fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
    val showTwoPane by component.showTwoPane.collectAsState()

    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade() + scale()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.DrawingChild -> {
                if (showTwoPane) {
                    /*TwoPane(
                        first = { DrawingContent(component = component.drawingComponent) },
                        second = { OutputContent(component = component.outputComponent) },
                        strategy = HorizontalTwoPaneStrategy(0.6f),
                        displayFeatures = emptyList(),
                    )*/
                } else {
                    DrawingContent(component = component.drawingComponent)
                }
            }
            is RootComponent.Child.OutputChild -> {
                if (showTwoPane) {
//                    TwoPane(
//                        first = { DrawingContent(component = component.drawingComponent) },
//                        second = { OutputContent(component = component.outputComponent) },
//                        strategy = HorizontalTwoPaneStrategy(0.6f),
//                        displayFeatures = emptyList(),
//                    )
                } else {
                    OutputContent(component = component.outputComponent)
                }
            }
            is RootComponent.Child.StoreChild -> StoreContent(component = child.component)
        }
    }
}