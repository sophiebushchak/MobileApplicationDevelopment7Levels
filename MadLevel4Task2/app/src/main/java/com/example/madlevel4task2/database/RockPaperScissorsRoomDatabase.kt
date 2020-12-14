package com.example.madlevel4task2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.tools.Converters

@Database(entities = [Game::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class RockPaperScissorsRoomDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private const val DATABASE_NAME = "RPS_HISTORY_DATABASE"

        @Volatile
        private var rockPaperScissorsRoomDatabaseInstance: RockPaperScissorsRoomDatabase? = null

        fun getDatabase(context: Context): RockPaperScissorsRoomDatabase? {
            if (rockPaperScissorsRoomDatabaseInstance == null) {
                synchronized(RockPaperScissorsRoomDatabase::class.java) {
                    if (rockPaperScissorsRoomDatabaseInstance == null) {
                        rockPaperScissorsRoomDatabaseInstance =
                            Room.databaseBuilder(
                                context.applicationContext,
                                RockPaperScissorsRoomDatabase::class.java,
                                DATABASE_NAME
                            ).build()
                    }
                }
            }
            return rockPaperScissorsRoomDatabaseInstance
        }
    }

}