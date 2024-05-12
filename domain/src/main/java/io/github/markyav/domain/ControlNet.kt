package io.github.markyav.domain

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Main interface to generate an image using ControlNet.
 */
interface ControlNet {
    suspend fun process(params: ControlNetParams): ImageBitmap?
}