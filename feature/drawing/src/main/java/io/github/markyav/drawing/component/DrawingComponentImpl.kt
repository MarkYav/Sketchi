package io.github.markyav.drawing.component

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.drawbox.controller.DrawBoxSubscription
import io.github.markyav.drawbox.controller.DrawController
import io.github.markyav.ui.util.mapState

class DrawingComponentImpl(
    componentContext: ComponentContext,
    val onFinishDrawing: (scribble: ImageBitmap) -> Unit,
    val onBackClick: () -> Unit,
) : DrawingComponent, ComponentContext by componentContext {
    override val drawController: DrawController = DrawController()
    override val isCanvasBlank = drawController.undoCount.mapState { it == 0 }
    private val drawnBitmap = drawController.getBitmap(512, DrawBoxSubscription.FinishDrawingUpdate)

    init {
        drawController.color.value = Color.Black
        drawController.strokeWidth.value = 30f
        drawController.canvasOpacity.value = 0.6f
    }

    override fun finishDrawing() {
        onFinishDrawing.invoke(addBackground(drawnBitmap.value))
    }

    override fun onBackClick() {
        this.onBackClick.invoke()
    }

    private fun addBackground(image: ImageBitmap): ImageBitmap {
        val bmOverlay = ImageBitmap(image.width, image.height, image.config)
        val canvas = Canvas(bmOverlay)
        val paint = Paint()
        paint.color = Color.White

        canvas.drawRect(Rect(Offset.Zero, Size(512f, 512f)), paint)
        canvas.drawImage(image, Offset.Zero, Paint())

        return bmOverlay
    }
}