package io.github.markyav.output.component

import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OutputComponentTest : OutputComponent {
    override val generatedImage: Value<OutputComponent.GeneratedImage> =
        MutableValue(OutputComponent.GeneratedImage.Loading)
    override val useSofganModel: StateFlow<Boolean> = MutableStateFlow(false)
    override val isSideBySideMode: StateFlow<Boolean> = MutableStateFlow(false)

    override fun onAngleChanged(x: Float, y: Float) {
    }

    override fun onModelChanged(useSofgan: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onBackClick() {
    }
}