package com.example.madlevel5task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madlevel5task2.model.Game

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameBacklogDatabase: RoomDatabase() {
    abstract fun backlogDao(): BacklogDao

    companion object {
        private const val DATABASE_NAME = "BACKLOG_DATABASE"

        @Volatile
        private var gameBacklogDatabaseInstance: GameBacklogDatabase? = null

        fun getDatabase(context: Context): GameBacklogDatabase? {
            if (gameBacklogDatabaseInstance == null) {
                synchronized(GameBacklogDatabase::class.java) {
                    if (gameBacklogDatabaseInstance == null) {
                        gameBacklogDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                GameBacklogDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return gameBacklogDatabaseInstance
        }
    }
}