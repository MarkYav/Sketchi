package io.github.markyav.data.room

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.domain.ControlNetParams
import io.github.markyav.domain.DiffusionModelParams
import java.time.LocalDateTime

@Entity
data class SavedControlNetParamsEntity(
    val createdTime: LocalDateTime,
    val scribble: Bitmap,
    val prompt: String,
    val numberOfSteps: Int = 10,
    val guidanceScale: Float = 9f,
    val seed: Int? = null,
    val additionalPrompt: String,
    val negativePrompt: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun SavedControlNetParamsEntity.toDto(): SavedControlNetParamsDto =
    SavedControlNetParamsDto(
        id = this.id,
        createdTime = this.createdTime,
        controlNetParams = ControlNetParams(
            scribble = this.scribble.asImageBitmap(),
            diffusionModelParams = DiffusionModelParams(
                prompt = this.prompt,
                numberOfSteps = this.numberOfSteps,
                guidanceScale = this.guidanceScale,
                seed = this.seed,
                additionalPrompt = this.additionalPrompt,
                negativePrompt = this.negativePrompt,
            )
        ),
    )

fun SavedControlNetParamsDto.toEntity(): SavedControlNetParamsEntity =
    SavedControlNetParamsEntity(
        createdTime = this.createdTime,
        scribble = this.controlNetParams.scribble.asAndroidBitmap(),
        prompt = this.controlNetParams.diffusionModelParams.prompt,
        numberOfSteps = this.controlNetParams.diffusionModelParams.numberOfSteps,
        guidanceScale = this.controlNetParams.diffusionModelParams.guidanceScale,
        seed = this.controlNetParams.diffusionModelParams.seed,
        additionalPrompt = this.controlNetParams.diffusionModelParams.additionalPrompt,
        negativePrompt = this.controlNetParams.diffusionModelParams.negativePrompt,
    ).also {
        it.id = this.id
    }