package com.example.madlevel4task2.tools

import androidx.room.TypeConverter
import com.example.madlevel4task2.model.Throw

class ThrowConverters {
    @TypeConverter
    fun fromStringThrowEnum(value: String): Throw? {
        return enumValueOf<Throw>(value)
    }

    @TypeConverter
    fun enumThrowToString(value: Throw?): String? {
        return value?.toString()
    }
}