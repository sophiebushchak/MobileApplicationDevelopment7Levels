package com.example.madlevel7task2.model

/**
 * A model for a quiz answer. Technically a quiz answer should perhaps not be a model.
 * However, for the sake of conciseness it is one. The advantage is that it can be potentially
 * extended to hold for example a URI to an image.
 */
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