package io.github.markyav.data.repository

import android.graphics.Bitmap
import java.time.LocalDateTime

data class SketchDto(
    val sketch: Bitmap,
    val createdTime: LocalDateTime,
) {
    var id: Int = 0
}