package io.github.markyav.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.markyav.data.repository.SketchDto
import io.github.markyav.data.room.convertor.BitmapConvertor
import io.github.markyav.data.room.convertor.TimeConvertor

@Database(entities = [SketchDto::class], version = 1, exportSchema = false)
@TypeConverters(BitmapConvertor::class, TimeConvertor::class)
internal abstract class MyDatabase : RoomDatabase() {
    internal abstract fun myDao(): SketchDao

    internal companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        internal fun getDatabase(context: Context): MyDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyDatabase::class.java, "my_database"
            ).build()
    }
}