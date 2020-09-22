package com.example.madlevel4task2.model

import com.example.madlevel4task2.R
import java.util.*

data class Game (
    val date: Date,
    val playerThrow: Throw,
    val opponentThrow: Throw,
    val result: GameResult
) {
    companion object {
        public fun getThrowImage(thrown: Throw): Int {
            if (thrown == Throw.PAPER) return R.drawable.paper
            if (thrown == Throw.SCISSORS) return R.drawable.scissors
            if (thrown == Throw.ROCK) return R.drawable.rock
            return 1;
        }
    }
}

