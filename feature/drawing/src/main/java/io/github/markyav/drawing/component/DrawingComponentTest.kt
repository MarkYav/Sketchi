package io.github.markyav.drawing.component

import io.github.markyav.drawbox.controller.DrawController
import kotlinx.coroutines.flow.StateFlow

class DrawingComponentTest: DrawingComponent {
    override val drawController: DrawController
        get() = TODO("Not yet implemented")
    override val isCanvasBlank: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun finishDrawing() {
        TODO("Not yet implemented")
    }

    override fun onBackClick() {
        TODO("Not yet implemented")
    }

}