package io.github.markyav.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.markyav.data.repository.SketchDto

@Dao
interface SketchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(dto: SketchDto): Long

    @Query("SELECT * FROM SketchDto WHERE id = :dtoId")
    suspend fun get(dtoId: Long): SketchDto

    @Query("SELECT * FROM SketchDto")
    suspend fun getAll(): List<SketchDto>
}