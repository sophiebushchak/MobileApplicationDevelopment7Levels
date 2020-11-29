package com.example.madlevel7task2.model

class Quiz(
    val quizName: String,
    val quizDescription: String,
    private val quizQuestions: MutableList<QuizQuestion> = mutableListOf()
) {
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
}
