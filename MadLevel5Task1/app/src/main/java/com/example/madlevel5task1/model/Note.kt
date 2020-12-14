package com.example.madlevel5task1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    @ColumnInfo(name = "note_title")
    var title: String,

    @ColumnInfo(name = "note_last_updated")
    var lastUpdated: Date,

    @ColumnInfo(name = "note_text")
    var text: String,

    @PrimaryKey
    @ColumnInfo(name = "note_id")
    var id: Long? = null
)