package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.database.RockPaperScissorsRoomDatabase
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameResult

class GameHistoryRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = RockPaperScissorsRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getGameHistory(): List<Game> {
        return gameDao.getGameHistory()
    }

    suspend fun insertIntoGameHistory(game: Game) {
        return gameDao.insertIntoGameHistory(game)
    }

    suspend fun deleteGameFromHistory(game: Game) {
        return gameDao.deleteGameFromHistory(game)
    }

    suspend fun deleteGameHistory() {
        return gameDao.deleteGameHistory()
    }

    suspend fun countAResult(gameResult: GameResult): Int {
        return gameDao.countAResult(gameResult)
    }
}