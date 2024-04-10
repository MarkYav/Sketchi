package io.github.markyav.drawing.component

import android.content.Context
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.data.repository.SketchDto
import io.github.markyav.data.room.SketchRepositoryImpl
import io.github.markyav.drawbox.controller.DrawBoxBackground
import io.github.markyav.drawbox.controller.DrawBoxSubscription
import io.github.markyav.drawbox.controller.DrawController
import io.github.markyav.ui.util.getBitmapFromImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class DrawingComponentImpl(
    componentContext: ComponentContext,
    private val applicationContext: Context,
    val generate: () -> Unit,
    val select: () -> Unit,
) : DrawingComponent, ComponentContext by componentContext {
    override val drawController: DrawController = DrawController()
    private val repository = SketchRepositoryImpl.getSketchRepositoryImpl(applicationContext)
    private val drawnBitmap = drawController.getBitmap(512, DrawBoxSubscription.FinishDrawingUpdate)
    //private val faceSegmentationModel: Model = FaceSegmentationSegNetModel(applicationContext)
    val scope = CoroutineScope(Dispatchers.IO)

    init {
        drawController.color.value = TODO() //drawingColors.first().color
        drawController.canvasOpacity.value = 0.6f
    }

    override fun getDrawing(): ImageBitmap {
        generate()
        return drawnBitmap.value
    }

    override fun selectSaved() {
        select()
    }

    override fun save() {
        scope.launch {
            repository.add(
                SketchDto(
                    sketch = drawnBitmap.value.asAndroidBitmap(),
                    createdTime = LocalDateTime.now()
                )
            )
        }
    }

    override fun import(bitmap: ImageBitmap) {
        scope.launch {
            /*val faceSegmentation = faceSegmentationModel.process(bitmap)
            if (faceSegmentation != null) {
                drawController.open(faceSegmentation)
            }*/
        }
    }

    override fun load(bitmap: ImageBitmap) {
        drawController.open(bitmap)
    }

    override fun onShowFaceChange() {
        /*if (drawController.background.value is DrawBoxBackground.NoBackground) {
            val faceTemplate = getBitmapFromImage(applicationContext, R.drawable.face_template_2)
            drawController.background.value = DrawBoxBackground.ImageBackground(faceTemplate)
        } else {
            drawController.background.value = DrawBoxBackground.NoBackground
        }*/
    }
}