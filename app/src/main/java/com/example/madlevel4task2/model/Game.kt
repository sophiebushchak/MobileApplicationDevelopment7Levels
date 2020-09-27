package com.example.madlevel4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madlevel4task2.R
import java.util.*

@Entity(tableName = "game_history_table")
data class Game (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "game_date")
    val date: Date,

    @ColumnInfo(name = "game_player_throw")
    val playerThrow: Throw,

    @ColumnInfo(name = "game_opponent_throw")
    val opponentThrow: Throw,

    @ColumnInfo(name = "game_result")
    val result: GameResult
) {
    companion object {
        /**
         * Returns a corresponding image for a given Throw value
         */
        public fun getThrowImage(thrown: Throw): Int {
            if (thrown == Throw.PAPER) return R.drawable.paper
            if (thrown == Throw.SCISSORS) return R.drawable.scissors
            if (thrown == Throw.ROCK) return R.drawable.rock
            return 1;
        }

        /**
         * Returns the result of a Rock Paper Scissors match based on the throws
         */
        public fun getResult(playerThrown: Throw, opponentThrown: Throw): GameResult {
            return if (playerThrown == Throw.PAPER && opponentThrown == Throw.ROCK
                || playerThrown == Throw.ROCK && opponentThrown == Throw.SCISSORS
                || playerThrown == Throw.SCISSORS && opponentThrown == Throw.PAPER) {
                GameResult.WIN
            } else if (playerThrown == opponentThrown) {
                GameResult.DRAW
            } else GameResult.LOSE
        }
    }
}

