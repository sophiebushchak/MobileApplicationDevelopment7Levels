package com.example.madlevel4task2.tools

import androidx.room.TypeConverter
import com.example.madlevel4task2.model.GameResult

class GameResultConverters {
    @TypeConverter
    fun fromStringGameResultEnum(value: String): GameResult? {
        return enumValueOf<GameResult>(value)
    }

    @TypeConverter
    fun enumGameResultToString(value: GameResult?): String? {
        return value?.toString()
    }
}