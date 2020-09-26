package com.example.madlevel5task2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel5task2.model.Game

@Dao
interface BacklogDao {
    @Query("SELECT * FROM game_backlog_table")
    fun getBacklog(): LiveData<List<Game>>

    @Insert
    suspend fun insertGame(game: Game)

    @Delete
    suspend fun deleteGameFromBacklog(game: Game)

    @Query("DELETE FROM game_backlog_table")
    suspend fun deleteBacklog()
}