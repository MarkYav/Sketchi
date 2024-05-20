package io.github.markyav.drawing.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.github.markyav.drawbox.box.DrawBox
import io.github.markyav.drawing.component.DrawingComponent
import io.github.markyav.drawing.component.DrawingComponentTest
import io.github.markyav.drawing.content.component.DrawingControl
import io.github.markyav.ui.util.AndroidPreviewDevices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingContent(component: DrawingComponent, modifier: Modifier = Modifier) {
    val isCanvasBlank by component.isCanvasBlank.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Draw scribble") },
                navigationIcon = {
                    IconButton(onClick = component::onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(it),
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
            }
            Column (modifier = Modifier.padding(16.dp)) {
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = component::finishDrawing,
                    enabled = !isCanvasBlank,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Done")
                }
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