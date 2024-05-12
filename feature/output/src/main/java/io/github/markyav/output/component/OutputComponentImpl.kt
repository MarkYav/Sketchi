package io.github.markyav.output.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.domain.ControlNet
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.model.NetworkControlNet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutputComponentImpl(
    componentContext: ComponentContext,
    params: ControlNetParams,
    val onBackClick: () -> Unit,
) : OutputComponent, ComponentContext by componentContext {
    override val generatedImage: MutableValue<OutputComponent.GeneratedImage> =
        MutableValue(OutputComponent.GeneratedImage.Loading)
    private val model: ControlNet = NetworkControlNet()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val image = model.process(params)
            if (image != null) {
                generatedImage.value = OutputComponent.GeneratedImage.Generated(image)
            } else {
                generatedImage.value = OutputComponent.GeneratedImage.Error
            }
        }
    }

    override fun onBackClick() {
        onBackClick.invoke()
    }
}