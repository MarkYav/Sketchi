package io.github.markyav.drawing.component

import io.github.markyav.drawbox.controller.DrawController

interface DrawingComponent {
    val drawController: DrawController

    fun finishDrawing()
    fun onBackClick()
}