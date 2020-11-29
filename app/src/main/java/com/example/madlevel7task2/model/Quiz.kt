package com.example.madlevel7task2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Quiz() : Parcelable {
    var quizName: String = ""
    var quizDescription: String = ""
    var quizQuestions: MutableList<QuizQuestion> = mutableListOf()

    constructor(quizName: String,
                quizDescription: String) : this() {
        this.quizName = quizName
        this.quizDescription = quizDescription
    }

    fun getQuestionAtIndex(index: Int): QuizQuestion {
        return quizQuestions[index]
    }

    fun getTotalQuestions(): Int {
        return quizQuestions.size
    }

    fun addQuestion(quizQuestion: QuizQuestion) {
        quizQuestions.add(quizQuestion)
    }

    fun getQuestions(): List<QuizQuestion> {
        return this.quizQuestions.toList()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Quiz

        if (quizName != other.quizName) return false
        if (quizDescription != other.quizDescription) return false
        if (quizQuestions != other.quizQuestions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quizName.hashCode()
        result = 31 * result + quizDescription.hashCode()
        result = 31 * result + quizQuestions.hashCode()
        return result
    }

    override fun toString(): String {
        val string = StringBuilder()
            .appendln("Quiz with name: $quizName")
            .appendln("Quiz with description: $quizDescription")
            .appendln("Quiz with questions:")

        for (question in quizQuestions) {
            string.appendln("-----------------------")
            string.appendln("$question")
        }

        return string.toString()
    }
}
