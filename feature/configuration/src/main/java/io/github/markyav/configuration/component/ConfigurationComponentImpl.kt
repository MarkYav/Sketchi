package io.github.markyav.configuration.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.domain.ADDITIONAL_PROMPT
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import io.github.markyav.domain.NEGATIVE_PROMPT
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.math.RoundingMode

class ConfigurationComponentImpl(
    componentContext: ComponentContext,
    val onDrawScribbleClicked: (ImageBitmap?) -> Unit,
    val onGenerateClicked: (params: ControlNetParams) -> Unit,
    val onSelectFromSavedClicked: () -> Unit,
) : ConfigurationComponent, ComponentContext by componentContext {
    override val scribble: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)
    override val prompt: MutableStateFlow<String> = MutableStateFlow("")
    override val runtime: MutableStateFlow<Float> = MutableStateFlow(30f)
    override val seed: MutableStateFlow<Int?> = MutableStateFlow(42)
    override val guidanceScale: MutableStateFlow<Float> = MutableStateFlow(9f)
    override val additionalPrompt: MutableStateFlow<String> = MutableStateFlow(ADDITIONAL_PROMPT)
    override val negativePrompt: MutableStateFlow<String> = MutableStateFlow(NEGATIVE_PROMPT)
    override val isGenerationEnabled: StateFlow<Boolean> = combineStates(scribble, prompt) { scribble, prompt ->
        scribble != null && prompt.isNotBlank()
    }

    override fun generate() {
        require(scribble.value != null)
        onGenerateClicked.invoke(
            ControlNetParams(
                scribble = scribble.value!!,
                diffusionModelParams = DiffusionModelParams(
                    prompt = prompt.value
                )
            )
        )
    }

    override fun updatePrompt(newPrompt: String) {
        prompt.value = newPrompt
    }

    override fun updateRuntime(newRuntime: Float) {
        runtime.value = newRuntime
    }

    override fun updateSeed(newSeed: String) {
        if (newSeed.all { char -> char.isDigit() }) {
            seed.value = newSeed.toIntOrNull()
        }
    }

    override fun updateGuidanceScale(newGuidanceScale: Float) {
        guidanceScale.value = newGuidanceScale.toBigDecimal().setScale(1, RoundingMode.HALF_EVEN).toFloat()
    }

    override fun updateAdditionalPrompt(newAdditionalPrompt: String) {
        additionalPrompt.value = newAdditionalPrompt
    }

    override fun updateNegativePrompt(newNegativePrompt: String) {
        negativePrompt.value = newNegativePrompt
    }

    override fun drawScribble() {
        onDrawScribbleClicked.invoke(scribble.value)
    }

    override fun selectFromSaved() {
        onSelectFromSavedClicked.invoke()
    }

    override fun deleteScribble() {
        scribble.value = null
    }

    override fun importControlNetParams(params: ControlNetParams) {
        TODO("Not yet implemented")
    }

    override fun updateScribble(newScribble: ImageBitmap) {
        scribble.value = newScribble
    }
}