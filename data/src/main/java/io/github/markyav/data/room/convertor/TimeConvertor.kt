package io.github.markyav.data.room.convertor

import androidx.room.TypeConverter
import java.time.LocalDateTime

class TimeConvertor {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        // https://stackoverflow.com/questions/54927913/room-localdatetime-typeconverter
        // https://developer.android.com/studio/write/java8-support#library-desugaring
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}