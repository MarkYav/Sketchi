package io.github.markyav.data.room

import android.content.Context
import io.github.markyav.data.repository.SavedControlNetParamsDto
import io.github.markyav.data.repository.SavedControlNetParamsRepository

class SavedControlNetParamsRepositoryImpl(
    private val savedControlNetParamsDao: SavedControlNetParamsDao
) : SavedControlNetParamsRepository {
    override suspend fun add(dto: SavedControlNetParamsDto): Long = savedControlNetParamsDao.add(dto.toEntity())

    override suspend fun get(dtoId: Long): SavedControlNetParamsDto = savedControlNetParamsDao.get(dtoId).toDto()

    override suspend fun getAll(): List<SavedControlNetParamsDto> = savedControlNetParamsDao.getAll().map { it.toDto() }

    companion object {
        @Volatile
        private var INSTANCE: SavedControlNetParamsRepositoryImpl? = null
        fun getSketchRepositoryImpl(context: Context): SavedControlNetParamsRepository =
            INSTANCE ?: synchronized(this) {
                val dao = MyDatabase.getDatabase(context).myDao()
                INSTANCE ?: SavedControlNetParamsRepositoryImpl(dao)
            }
    }
}