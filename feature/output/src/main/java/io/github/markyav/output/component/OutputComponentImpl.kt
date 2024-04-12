package io.github.markyav.output.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.model.ControlNet
import io.github.markyav.model.MockControlNet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OutputComponentImpl(
    componentContext: ComponentContext,
    params: ControlNet.ControlNetParams,
    val onBackClick: () -> Unit,
) : OutputComponent, ComponentContext by componentContext {
    override val generatedImage: MutableValue<OutputComponent.GeneratedImage> =
        MutableValue(OutputComponent.GeneratedImage.Loading)
    private val model: ControlNet = MockControlNet()

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