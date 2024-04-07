package io.github.markyav.data.repository

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class SketchDto(
    val sketch: Bitmap,
    val createdTime: LocalDateTime,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}