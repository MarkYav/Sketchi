package io.github.markyav.configuration.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value
import io.github.markyav.domain.ControlNetParams
import kotlinx.coroutines.flow.StateFlow

class ConfigurationComponentTest : ConfigurationComponent {
    override val prompt: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val runtime: StateFlow<Float>
        get() = TODO("Not yet implemented")
    override val seed: StateFlow<Int?>
        get() = TODO("Not yet implemented")
    override val guidanceScale: StateFlow<Float>
        get() = TODO("Not yet implemented")
    override val additionalPrompt: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val negativePrompt: StateFlow<String>
        get() = TODO("Not yet implemented")
    override val scribble: StateFlow<ImageBitmap?>
        get() = TODO("Not yet implemented")
    override val isGenerationEnabled: StateFlow<Boolean>
        get() = TODO("Not yet implemented")

    override fun generate() {
        TODO("Not yet implemented")
    }

    override fun updatePrompt(newPrompt: String) {
        TODO("Not yet implemented")
    }

    override fun updateRuntime(newRuntime: Float) {
        TODO("Not yet implemented")
    }

    override fun updateSeed(newSeed: String) {
        TODO("Not yet implemented")
    }

    override fun updateGuidanceScale(newGuidanceScale: Float) {
        TODO("Not yet implemented")
    }

    override fun updateAdditionalPrompt(newAdditionalPrompt: String) {
        TODO("Not yet implemented")
    }

    override fun updateNegativePrompt(newNegativePrompt: String) {
        TODO("Not yet implemented")
    }

    override fun drawScribble() {
        TODO("Not yet implemented")
    }

    override fun deleteScribble() {
        TODO("Not yet implemented")
    }

    override fun selectFromSaved() {
        TODO("Not yet implemented")
    }

    override fun importControlNetParams(params: ControlNetParams) {
        TODO("Not yet implemented")
    }

    override fun updateScribble(scribble: ImageBitmap) {
        TODO("Not yet implemented")
    }

}