package io.github.markyav.output.component

import android.content.Context
import android.util.Log
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import io.github.markyav.drawbox.util.combineStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.atan

class OutputComponentImpl(
    componentContext: ComponentContext,
    // TODO: add ControlNet params
    val onBackClick: () -> Unit,
) : OutputComponent, ComponentContext by componentContext {
    override val generatedImage: MutableValue<OutputComponent.GeneratedImage> =
        MutableValue(OutputComponent.GeneratedImage.Loading)


    override fun onBackClick() {
        onBackClick.invoke()
    }
}