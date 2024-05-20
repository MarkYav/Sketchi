package io.github.markyav.data.repository

import io.github.markyav.domain.ControlNetParams
import java.time.LocalDateTime

data class SavedControlNetParamsDto(
    val id: Int = 0, // TODO: make default = 0 OR make it "Int?"
    val createdTime: LocalDateTime,
    val controlNetParams: ControlNetParams,
)