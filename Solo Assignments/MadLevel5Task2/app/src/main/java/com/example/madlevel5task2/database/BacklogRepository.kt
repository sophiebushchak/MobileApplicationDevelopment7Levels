package com.example.madlevel5task2.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.madlevel5task2.model.Game

class BacklogRepository(context: Context) {
    private val backlogDao: BacklogDao

    init {
        val database = GameBacklogDatabase.getDatabase(context)
        backlogDao = database!!.backlogDao()
    }

    fun getBacklog(): LiveData<List<Game>> {
        return backlogDao.getBacklog()
    }

    suspend fun insertGame(game: Game) {
        return backlogDao.insertGame(game)
    }

    suspend fun deleteGameFromBacklog(game: Game) {
        return backlogDao.deleteGameFromBacklog(game)
    }

    suspend fun deleteBacklog() {
        return backlogDao.deleteBacklog()
    }
}