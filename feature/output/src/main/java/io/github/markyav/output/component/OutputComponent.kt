package io.github.markyav.output.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value

interface OutputComponent {
    val generatedImage: Value<GeneratedImage>

    fun onBackClick()

    sealed interface GeneratedImage {
        data object Loading : GeneratedImage
        data class Generated(val image: ImageBitmap) : GeneratedImage
        data object Error : GeneratedImage
    }
}