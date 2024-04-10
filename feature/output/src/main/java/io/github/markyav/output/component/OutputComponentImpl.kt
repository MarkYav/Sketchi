package io.github.markyav.output.component

import android.content.Context
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.atan

class OutputComponentImpl(
    componentContext: ComponentContext,
    drawnBitmap: StateFlow<ImageBitmap>,
    override val isSideBySideMode: StateFlow<Boolean>,
    applicationContext: Context,
    val onBackClick: () -> Unit,
) : OutputComponent, ComponentContext by componentContext {
    override val generatedImage: MutableValue<OutputComponent.GeneratedImage> =
        MutableValue(OutputComponent.GeneratedImage.Loading)
    override val useSofganModel: MutableStateFlow<Boolean> = MutableStateFlow(true)

//    private val sofganModel = SofganModel(applicationContext)
//    private val EG3DModel = EG3DModel(applicationContext)

    private val offset = MutableStateFlow(Offset(0f, 0f))
//    private val usedModelFlow = combineStates(drawnBitmap, useSofganModel, offset) { bitmap, useSofgan, offset ->
//        if (useSofgan) {
//            UsedModel.Sofgan(bitmap)
//        } else {
//            UsedModel.EG3D(bitmap, offset)
//        }
//    }

    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            usedModelFlow.collectLatest { usedModel ->
//                when (usedModel) {
//                    is UsedModel.Sofgan -> {
//                        val image = sofganModel.process(usedModel.imageBitmap)
//                        if (image != null) {
//                            generatedImage.value = OutputComponent.GeneratedImage.Generated(image)
//                        }
//                    }
//                    is UsedModel.EG3D -> {
//                        val image = EG3DModel.process(usedModel.imageBitmap, usedModel.offset)
//                        if (image != null) {
//                            generatedImage.value = OutputComponent.GeneratedImage.Generated(image)
//                        }
//                    }
//                }
//            }
//        }
    }

    override fun onAngleChanged(x: Float, y: Float) {
        val deltaPhi = -calDegree(x.toDouble() / 500).toFloat()
        val deltaTheta = -calDegree(y.toDouble() / 500).toFloat()
        offset.value = offset.value.plus(Offset(deltaPhi, deltaTheta))
        Log.i("TAG_aaa", "onAngleChanged: ${offset.value.x}, ${offset.value.y}")
    }

    private fun calDegree(t: Double): Double { // x/r = t, t << 1
        return atan(t / (1 - t*t))
    }

    override fun onModelChanged(useSofgan: Boolean) {
        if (useSofgan == useSofganModel.value) {
            return
        }
        useSofganModel.value = useSofgan
        if (useSofgan) {
            offset.value = Offset(0f, 0f)
        }
    }

    override fun onBackClick() {
        this.onBackClick.invoke()
    }

    sealed interface UsedModel {
        data class Sofgan(val imageBitmap: ImageBitmap) : UsedModel
        data class EG3D(val imageBitmap: ImageBitmap, val offset: Offset) : UsedModel
    }
}