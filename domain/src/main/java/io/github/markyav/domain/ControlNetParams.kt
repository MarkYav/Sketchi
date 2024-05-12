package io.github.markyav.domain

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

/**
 * Params for image generation using ControlNet.
 *
 * @param scribble the visual condition specific to ControlNet
 * @param diffusionModelParams the params of inner Diffusion Model
 */
@Serializable
data class ControlNetParams(
    @Serializable
    val scribble: ImageBitmap,
    @Serializable
    val diffusionModelParams: DiffusionModelParams,
)