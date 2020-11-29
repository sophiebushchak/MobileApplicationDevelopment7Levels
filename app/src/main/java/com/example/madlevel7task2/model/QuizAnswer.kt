package com.example.madlevel7task2.model

class QuizAnswer() {
    var answerText: String = ""

    constructor(answerText: String) : this() {
        this.answerText = answerText
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuizAnswer

        if (answerText != other.answerText) return false

        return true
    }

    override fun hashCode(): Int {
        return answerText.hashCode()
    }

    override fun toString(): String {
        return "QuizAnswer with text: $answerText"
    }


}