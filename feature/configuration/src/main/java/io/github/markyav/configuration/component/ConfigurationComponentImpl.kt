package io.github.markyav.configuration.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.data.repository.SavedControlNetParamsRepository
import io.github.markyav.domain.ADDITIONAL_PROMPT
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import io.github.markyav.domain.NEGATIVE_PROMPT
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.time.LocalDateTime

class ConfigurationComponentImpl(
    componentContext: ComponentContext,
    val repository: SavedControlNetParamsRepository,
    val onDrawScribbleClicked: (ImageBitmap?) -> Unit,
    val onGenerateClicked: (params: ControlNetParams) -> Unit,
    val onSelectFromSavedClicked: () -> Unit,
    val makeToast: (String) -> Unit,
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
        scribble.value = params.scribble
        prompt.value = params.diffusionModelParams.prompt
        runtime.value = params.diffusionModelParams.numberOfSteps.toFloat()
        seed.value = params.diffusionModelParams.seed
        guidanceScale.value = params.diffusionModelParams.guidanceScale
        additionalPrompt.value = params.diffusionModelParams.additionalPrompt
        negativePrompt.value = params.diffusionModelParams.negativePrompt
    }

    override fun saveConfiguration() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.add(SavedControlNetParamsDto(
                createdTime = LocalDateTime.now(),
                controlNetParams = ControlNetParams(
                    scribble = scribble.value!!,
                    diffusionModelParams = DiffusionModelParams(
                        prompt = prompt.value,
                        numberOfSteps = runtime.value.toInt(),
                        seed = seed.value,
                        guidanceScale = guidanceScale.value,
                        additionalPrompt = additionalPrompt.value,
                    )
                )
            ))
        }
        makeToast("Configuration saved")
    }

    override fun updateScribble(scribble: ImageBitmap) {
        this.scribble.value = scribble
    }
}