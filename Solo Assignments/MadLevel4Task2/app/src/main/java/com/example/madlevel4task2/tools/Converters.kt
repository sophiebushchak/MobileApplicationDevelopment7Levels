package com.example.madlevel4task2.tools

import androidx.room.TypeConverter
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.model.Throw
import java.util.*

class Converters {
    @TypeConverter
    fun fromStringGameResultEnum(value: String): GameResult? {
        return enumValueOf<GameResult>(value)
    }

    @TypeConverter
    fun enumGameResultToString(value: GameResult?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromStringThrowEnum(value: String): Throw? {
        return enumValueOf<Throw>(value)
    }

    @TypeConverter
    fun enumThrowToString(value: Throw?): String? {
        return value?.toString()
    }
}