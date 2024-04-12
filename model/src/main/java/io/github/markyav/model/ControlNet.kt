package io.github.markyav.model

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.serialization.Serializable

interface ControlNet {
    suspend fun process(params: ControlNetParams): ImageBitmap?

    /**
     * Main interface to generate an image using ControlNet.
     *
     * @param scribble the visual condition specific to ControlNet
     * @param prompt text prompt for Stable Diffusion describing the content
     * @param numberOfSteps number of diffusion steps
     * @param guidanceScale
     * @param seed null denotes a random seed
     */
    @Serializable
    data class ControlNetParams(
        @Serializable
        val scribble: ImageBitmap,
        @Serializable
        val prompt: String,
        @Serializable @IntRange(from = 1, to = 100)
        val numberOfSteps: Int = 20,
        @Serializable @FloatRange(from = 0.1, to = 30.0)
        val guidanceScale: Float = 9f,
        @Serializable @IntRange(from = 0, to = 2147483647)
        val seed: Int? = null,
        @Serializable
        val additionalPrompt: String = "best quality, extremely detailed",
        @Serializable
        val negativePrompt: String = "longbody, lowres, bad anatomy, bad hands, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality",
//        numberOfImages: Int = 1, // 1
//        imageResolution: Int = 768, // 256-512-768
    )
}