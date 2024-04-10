package io.github.markyav.drawing.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.MaterialTheme
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentTest
import io.github.markyav.ui.util.AndroidPreviewDevices
import io.github.markyav.drawbox.box.DrawBox
import io.github.markyav.drawbox.controller.DrawController

@Composable
fun DrawingContent(component: DrawingComponent, modifier: Modifier = Modifier) {
    val controller = remember { DrawController() }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            DrawBox(
                controller = controller,
                modifier = Modifier
                    .shadow(elevation = 4.dp)
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .weight(1f, fill = false)
            )
            Divider(modifier = Modifier.size(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                minLines = 3,
                maxLines = 6,
                label = { Text(text = "Enter image description") }
            )

        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Generate")
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