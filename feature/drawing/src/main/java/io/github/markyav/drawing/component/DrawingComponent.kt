package io.github.markyav.drawing.component

import androidx.compose.ui.graphics.ImageBitmap
import io.github.markyav.drawbox.controller.DrawController

interface DrawingComponent {
    val drawController: DrawController

    fun getDrawing(): ImageBitmap
    fun selectSaved()
    fun save()
    fun import(bitmap: ImageBitmap)
    fun load(bitmap: ImageBitmap)
    fun onShowFaceChange()
}