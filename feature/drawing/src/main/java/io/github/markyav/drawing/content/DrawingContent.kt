package io.github.markyav.drawing.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import io.github.markyav.drawbox.box.DrawBox
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentTest
import io.github.markyav.drawing.content.component.DrawingControl
import io.github.markyav.ui.util.AndroidPreviewDevices

@Composable
fun DrawingContent(component: DrawingComponent, modifier: Modifier = Modifier) {
    val prompt by component.prompt.subscribeAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            DrawBox(
                controller = component.drawController,
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 4.dp)
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .weight(1f, fill = false)
            )
            DrawingControl(drawController = component.drawController)
            OutlinedTextField(
                value = prompt,
                onValueChange = { newPrompt -> component.updatePrompt(newPrompt)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                minLines = 3,
                maxLines = 6,
                label = { Text(text = "Enter image description") }
            )
            TextButton(
                onClick = { /*TODO: add advanced options*/ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = "Advanced Settings")
            }
        }
        Column (modifier = Modifier.padding(16.dp)) {
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = component::generate,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Generate")
            }
        }
    }
}

@AndroidPreviewDevices
@Composable
fun DrawingContentPreview() {
    MaterialTheme {
        DrawingContent(component = DrawingComponentTest())
    }
}