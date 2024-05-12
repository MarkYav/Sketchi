package io.github.markyav.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedControlNetParamsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(dto: SavedControlNetParamsEntity): Long

    @Query("SELECT * FROM SavedControlNetParamsEntity WHERE id = :dtoId")
    suspend fun get(dtoId: Long): SavedControlNetParamsEntity

    @Query("SELECT * FROM SavedControlNetParamsEntity")
    suspend fun getAll(): List<SavedControlNetParamsEntity>
}