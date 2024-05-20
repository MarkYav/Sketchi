package io.github.markyav.drawing.component

import io.github.markyav.drawbox.controller.DrawController
import kotlinx.coroutines.flow.StateFlow

interface DrawingComponent {
    val drawController: DrawController
    val isCanvasBlank: StateFlow<Boolean>

    fun finishDrawing()
    fun onBackClick()
}