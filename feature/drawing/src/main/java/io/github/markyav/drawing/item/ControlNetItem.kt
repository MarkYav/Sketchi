package io.github.markyav.drawing.item

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Main interface to generate an image using ControlNet.
 *
 * @param scribble the visual condition specific to ControlNet
 * @param prompt text prompt for Stable Diffusion describing the content
 * @param numberOfSteps number of diffusion steps
 * @param guidanceScale
 * @param seed null denotes a random seed
 * @param additionalPrompt
 * @param negativePrompt
 */
data class ControlNetItem(
    val scribble: ImageBitmap,
    val prompt: String,
    @IntRange(from = 1, to = 100)
    val numberOfSteps: Int = 20,
    @FloatRange(from = 0.1, to = 30.0)
    val guidanceScale: Float = 9f,
    @IntRange(from = 0, to = 2147483647)
    val seed: Int? = null,
    val additionalPrompt: String = "best quality, extremely detailed",
    val negativePrompt: String = "longbody, lowres, bad anatomy, bad hands, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality",
)
