package io.github.markyav.data.repository

interface SavedControlNetParamsRepository {
    suspend fun add(dto: SavedControlNetParamsDto): Long
    suspend fun get(dtoId: Long): SavedControlNetParamsDto
    suspend fun getAll(): List<SavedControlNetParamsDto>
}