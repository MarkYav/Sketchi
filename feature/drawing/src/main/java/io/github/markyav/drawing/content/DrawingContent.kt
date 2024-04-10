package io.github.markyav.drawing.content

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentTest
import io.github.markyav.ui.util.AndroidPreviewDevices

@Composable
fun DrawingContent(component: DrawingComponent, modifier: Modifier = Modifier) {
    BoxWithConstraints {
        val isWide = this.maxWidth >= this.maxHeight
        /*if (isWide) {
            OutputContentWide(component, modifier)
        } else {
            OutputContentNarrow(component, modifier)
        }*/
    }
}

@AndroidPreviewDevices
@Composable
fun DrawingContentPreview() {
    MaterialTheme {
        DrawingContent(component = DrawingComponentTest())
    }
}