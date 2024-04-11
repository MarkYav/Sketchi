package io.github.markyav.drawing.component

import com.arkivanov.decompose.value.Value
import io.github.markyav.drawbox.controller.DrawController

class DrawingComponentTest: DrawingComponent {
    override val drawController: DrawController = DrawController()
    override val prompt: Value<String>
        get() = TODO("Not yet implemented")

    override fun generate() {
        TODO("Not yet implemented")
    }

    override fun updatePrompt(newPrompt: String) {
        TODO("Not yet implemented")
    }
}