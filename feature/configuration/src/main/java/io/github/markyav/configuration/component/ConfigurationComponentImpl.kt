package io.github.markyav.configuration.component

import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ConfigurationComponentImpl(
    componentContext: ComponentContext,
    val onDrawScribbleClicked: (ImageBitmap?) -> Unit,
    val onGenerateClicked: (params: ControlNetParams) -> Unit,
    val onSelectFromSavedClicked: () -> Unit,
) : ConfigurationComponent, ComponentContext by componentContext {
    override val scribble: MutableStateFlow<ImageBitmap?> = MutableStateFlow(null)
    override val prompt: MutableStateFlow<String> = MutableStateFlow("")
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