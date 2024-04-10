package io.github.markyav.output.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.markyav.output.component.OutputComponent
import io.github.markyav.output.component.OutputComponentTest
import io.github.markyav.ui.util.AndroidPreviewDevices

@Composable
fun OutputContent(component: OutputComponent, modifier: Modifier = Modifier) {
    val generatedImage by component.generatedImage.subscribeAsState()
    val useSofganModel by component.useSofganModel.collectAsState()
    val isSideBySideMode by component.isSideBySideMode.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        when (generatedImage) {
            is OutputComponent.GeneratedImage.Generated -> Image(
                bitmap = (generatedImage as OutputComponent.GeneratedImage.Generated).image,
                contentDescription = null,
                modifier = Modifier
                    .shadow(elevation = 4.dp)
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .weight(1f, fill = false)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            component.onAngleChanged(dragAmount.x, dragAmount.y)
                        }
                    },
            )
            is OutputComponent.GeneratedImage.Loading -> Surface(
                modifier = Modifier
                    .shadow(elevation = 4.dp)
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .weight(1f, fill = false),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (!isSideBySideMode) {
            TextButton(onClick = component::onBackClick) {
                Text(text = "Return")
            }
        }
        Column(Modifier.selectableGroup()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = useSofganModel,
                    onClick = { component.onModelChanged(true) },
                )
                Text(text = "use Sofgan model")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !useSofganModel,
                    onClick = { component.onModelChanged(false) },
                )
                Text(text = "use EG3D model")
            }
        }
    }
}

@AndroidPreviewDevices
@Composable
fun OutputContentPreview() {
    MaterialTheme {
        OutputContent(component = OutputComponentTest())
    }
}