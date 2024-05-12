package io.github.markyav.domain

import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import kotlinx.serialization.Serializable

/**
 * Params for image generation using Diffusion Model.
 *
 * @param prompt text prompt for Stable Diffusion describing the content
 * @param numberOfSteps number of diffusion steps
 * @param guidanceScale
 * @param seed null denotes a random seed
 */
@Serializable
data class DiffusionModelParams(
    @Serializable
    val prompt: String,
    @Serializable @IntRange(from = 1, to = 100)
    val numberOfSteps: Int = 10,
    @Serializable @FloatRange(from = 0.1, to = 30.0)
    val guidanceScale: Float = 9f,
    @Serializable @IntRange(from = 0, to = 2147483647)
    val seed: Int? = null,
    @Serializable
    val additionalPrompt: String = ADDITIONAL_PROMPT,
    @Serializable
    val negativePrompt: String = NEGATIVE_PROMPT,
//        numberOfImages: Int = 1, // 1
//        imageResolution: Int = 768, // 256-512-768
)

const val ADDITIONAL_PROMPT: String = "best quality, extremely detailed"
const val NEGATIVE_PROMPT: String  = "longbody, lowres, bad anatomy, bad hands, missing fingers, extra digit, fewer digits, cropped, worst quality, low quality"
