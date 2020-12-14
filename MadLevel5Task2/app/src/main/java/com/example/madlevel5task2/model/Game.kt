package com.example.madlevel5task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game_backlog_table")
data class Game(
    @ColumnInfo(name="game_title")
    val title: String,

    @ColumnInfo(name="game_platform")
    val platform: String,

    @ColumnInfo(name="game_release_date")
    val releaseDate: Date,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val id: Long? = null
)