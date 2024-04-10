package io.github.markyav.drawing.component

import androidx.compose.ui.graphics.ImageBitmap
import io.github.markyav.drawbox.controller.DrawController

class DrawingComponentTest: DrawingComponent {
    override val drawController: DrawController = DrawController()

    override fun getDrawing(): ImageBitmap {
        TODO("Not yet implemented")
    }

    override fun selectSaved() {
        TODO("Not yet implemented")
    }

    override fun save() {
        TODO("Not yet implemented")
    }

    override fun import(bitmap: ImageBitmap) {
        TODO("Not yet implemented")
    }

    override fun load(bitmap: ImageBitmap) {
        TODO("Not yet implemented")
    }

    override fun onShowFaceChange() {
        TODO("Not yet implemented")
    }
}