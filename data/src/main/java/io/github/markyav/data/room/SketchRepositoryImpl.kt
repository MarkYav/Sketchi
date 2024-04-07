package io.github.markyav.data.room

import android.content.Context
import io.github.markyav.data.repository.SketchDto
import io.github.markyav.data.repository.SketchRepository

class SketchRepositoryImpl(
    private val sketchDao: SketchDao
) : SketchRepository {
    override suspend fun add(dto: SketchDto): Long = sketchDao.add(dto)

    override suspend fun get(dtoId: Long): SketchDto = sketchDao.get(dtoId)

    override suspend fun getAll(): List<SketchDto> = sketchDao.getAll()

    companion object {
        @Volatile
        private var INSTANCE: SketchRepositoryImpl? = null
        fun getSketchRepositoryImpl(context: Context): SketchRepository =
            INSTANCE ?: synchronized(this) {
                val dao = MyDatabase.getDatabase(context).myDao()
                INSTANCE ?: SketchRepositoryImpl(dao)
            }
    }
}