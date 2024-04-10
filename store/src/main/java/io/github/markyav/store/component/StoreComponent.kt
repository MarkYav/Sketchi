package io.github.markyav.store.component

import androidx.compose.ui.graphics.ImageBitmap
import com.arkivanov.decompose.value.Value
import io.github.markyav.store.item.SavedSketchItem

interface StoreComponent {
    val savedSketches: Value<List<SavedSketchItem>>

    fun select(bitmap: ImageBitmap)
    fun onBackClick()
}