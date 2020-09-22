package com.example.madlevel4task2.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel4task2.model.Game

@Dao
interface ProductDao {
    @Query("SELECT * FROM game_history_table")
    suspend fun getGameHistory(): List<Game>

    @Insert
    suspend fun insertIntoGameHistory(game: Game)

    @Delete
    suspend fun deleteGameFromHistory(game: Game)

    @Query("DELETE FROM game_history_table")
    suspend fun deleteGameHistory()
}