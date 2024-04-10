package io.github.markyav.output.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow

interface OutputComponent {
    val generatedImage: Value<GeneratedImage>
    val useSofganModel: StateFlow<Boolean>
    val isSideBySideMode: StateFlow<Boolean>

    fun onAngleChanged(x: Float, y: Float)
    fun onModelChanged(useSofgan: Boolean)
    fun onBackClick()

    sealed interface GeneratedImage {
        object Loading : GeneratedImage
        data class Generated(val image: ImageBitmap) : GeneratedImage
    }
}