package io.github.markyav.drawing.content.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.markyav.drawbox.controller.DrawController

@Composable
fun DrawingControl(
    drawController: DrawController,
) {
    val strokeWidth by drawController.strokeWidth.collectAsState()

    val undoCount by drawController.undoCount.collectAsState()
    val redoCount by drawController.redoCount.collectAsState()
    val enableUndo by remember { derivedStateOf { undoCount > 0 } }
    val enableRedo by remember { derivedStateOf { redoCount > 0 } }

    Row(Modifier.padding(horizontal = 8.dp)) {
        Slider(
            value = strokeWidth,
            onValueChange = { drawController.strokeWidth.value = it },
            valueRange = 10f..60f,
            modifier = Modifier.weight(1f),
            steps = 8,
        )
        IconButton(onClick = drawController::undo, enabled = enableUndo) {
            Icon(imageVector = Icons.AutoMirrored.Default.Undo, contentDescription = "undo")
        }
        IconButton(onClick = drawController::redo, enabled = enableRedo) {
            Icon(imageVector = Icons.AutoMirrored.Default.Redo, contentDescription = "redo")
        }
        IconButton(onClick = drawController::reset, enabled = enableUndo || enableRedo) {
            Icon(imageVector = Icons.Default.DeleteForever, contentDescription = "reset")
        }
    }
}