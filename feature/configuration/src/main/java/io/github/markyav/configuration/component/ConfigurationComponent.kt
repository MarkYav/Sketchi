package io.github.markyav.configuration.component

import androidx.compose.ui.graphics.ImageBitmap
import io.github.markyav.domain.ControlNetParams
import kotlinx.coroutines.flow.StateFlow

interface ConfigurationComponent {
    val isGenerationEnabled: StateFlow<Boolean> // for generate button

    val scribble: StateFlow<ImageBitmap?>
    val prompt: StateFlow<String>
    val runtime: StateFlow<Float>
    val seed: StateFlow<Int?>
    val guidanceScale: StateFlow<Float>
    val additionalPrompt: StateFlow<String>
    val negativePrompt: StateFlow<String>

    fun generate()

    fun drawScribble()
    fun deleteScribble()
    fun updateScribble(scribble: ImageBitmap)

    fun updatePrompt(newPrompt: String)
    fun updateRuntime(newRuntime: Float)
    fun updateSeed(newSeed: String)
    fun updateGuidanceScale(newGuidanceScale: Float)
    fun updateAdditionalPrompt(newAdditionalPrompt: String)
    fun updateNegativePrompt(newNegativePrompt: String)

    fun selectFromSaved()
    fun importControlNetParams(params: ControlNetParams)
    fun saveConfiguration()
}