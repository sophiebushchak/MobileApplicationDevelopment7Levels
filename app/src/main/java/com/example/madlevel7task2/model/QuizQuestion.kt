package com.example.madlevel7task2.model

class QuizQuestion() {
    lateinit var quizQuestionText: String
    lateinit var quizAnswers: List<QuizAnswer>
    lateinit var correctAnswer: QuizAnswer

    constructor(
        quizQuestionText: String,
        quizAnswers: List<QuizAnswer>,
        correctAnswer: QuizAnswer
    ) : this() {
        this.quizQuestionText = quizQuestionText
        this.quizAnswers = quizAnswers
        this.correctAnswer = correctAnswer
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuizQuestion

        if (quizQuestionText != other.quizQuestionText) return false
        if (quizAnswers != other.quizAnswers) return false
        if (correctAnswer != other.correctAnswer) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quizQuestionText.hashCode()
        result = 31 * result + quizAnswers.hashCode()
        result = 31 * result + correctAnswer.hashCode()
        return result
    }

    override fun toString(): String {
        val string = StringBuilder()
            .appendln("QuizQuestion with text: $quizQuestionText")
            .appendln("With answers:")

        for (quizAnswer in quizAnswers) {
            string.appendln("$quizAnswer")
        }

        string.appendln("With correct answer: $correctAnswer")
        return string.toString()
    }
}
