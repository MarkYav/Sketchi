package io.github.markyav.configuration.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.drawing.component.DrawingComponent
import kotlinx.coroutines.flow.StateFlow

interface ConfigurationComponent {
    val prompt: StateFlow<String>
    val scribble: StateFlow<ImageBitmap?>
    val isGenerationEnabled: StateFlow<Boolean>

    fun generate()
    fun updatePrompt(newPrompt: String)
    fun drawScribble()
    fun deleteScribble()
    fun selectFromSaved()
    fun importControlNetParams(params: ControlNetParams)
    fun updateScribble(scribble: ImageBitmap)
}