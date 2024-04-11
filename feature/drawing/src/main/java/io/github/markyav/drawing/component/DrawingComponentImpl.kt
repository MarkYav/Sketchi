package io.github.markyav.drawing.component

import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.drawbox.controller.DrawBoxSubscription
import io.github.markyav.drawbox.controller.DrawController
import io.github.markyav.drawing.item.ControlNetItem

class DrawingComponentImpl(
    componentContext: ComponentContext,
    val onGenerateClicked: (params: ControlNetItem) -> Unit,
    val onSelectClicked: () -> Unit,
) : DrawingComponent, ComponentContext by componentContext {
    override val drawController: DrawController = DrawController()
    override val prompt: MutableValue<String> = MutableValue("")
    private val drawnBitmap = drawController.getBitmap(512, DrawBoxSubscription.FinishDrawingUpdate)

    init {
        drawController.color.value = Color.Black
        drawController.strokeWidth.value = 30f
        drawController.canvasOpacity.value = 0.6f
    }

    override fun generate() {
        onGenerateClicked.invoke(
            ControlNetItem(
                scribble = drawnBitmap.value,
                prompt = prompt.value,
            )
        )
    }

    override fun updatePrompt(newPrompt: String) {
        prompt.value = newPrompt
    }
}