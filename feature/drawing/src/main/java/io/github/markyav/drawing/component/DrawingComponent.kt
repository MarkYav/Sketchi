package io.github.markyav.drawing.component

import com.arkivanov.decompose.value.Value
import io.github.markyav.drawbox.controller.DrawController

interface DrawingComponent {
    val drawController: DrawController
    val prompt: Value<String>

    fun generate()
    fun updatePrompt(newPrompt: String)
}