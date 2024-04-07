package io.github.markyav.ui.util

import java.time.LocalDateTime
import java.time.Duration
import kotlin.time.toKotlinDuration

fun LocalDateTime.toTimeAgo(): String {
    //val diff = LocalDateTime.from(this)
    val now = LocalDateTime.now()

    val duration = Duration.between(this, now).toKotlinDuration()
    return when {
        duration.inWholeMinutes < 2 -> "now"
        duration.inWholeMinutes < 60 -> "${duration.inWholeMinutes} minutes ago"
        duration.inWholeHours < 24 -> "${duration.inWholeHours} hours ago"
        duration.inWholeDays < 2 -> "yesterday"
        duration.inWholeDays < 7 -> "on ${this.dayOfWeek} at ${this.hour}:${this.minute}"
        duration.inWholeDays < 30 -> "on ${this.dayOfMonth} ${this.month} at ${this.hour}:${this.minute}"
        duration.inWholeDays < 365 -> "on ${this.dayOfMonth} ${this.month} ${this.year} at ${this.hour}:${this.minute}"
        else -> "on ${this.dayOfMonth} ${this.month} ${this.year} at ${this.hour}:${this.minute}"

        /*duration.inWholeDays < 7 -> "${duration.inWholeDays} days ago"
        duration.inWholeDays < 30 -> "${duration.inWholeDays / 7} weeks ago"
        duration.inWholeDays < 365 -> "${duration.inWholeDays / 30} months ago"
        else -> "${duration.inWholeDays / 365} years ago"*/
    }
}