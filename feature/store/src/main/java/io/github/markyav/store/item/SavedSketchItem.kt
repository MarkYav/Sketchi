package io.github.markyav.store.item

import androidx.compose.ui.graphics.ImageBitmap

data class SavedSketchItem(
    val sketch: ImageBitmap,
    val createdTime: String,
)