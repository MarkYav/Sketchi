package io.github.markyav.data.repository

interface SketchRepository {
    suspend fun add(dto: SketchDto): Long
    suspend fun get(dtoId: Long): SketchDto
    suspend fun getAll(): List<SketchDto>
}