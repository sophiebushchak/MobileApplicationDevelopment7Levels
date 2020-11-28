package com.example.madlevel7task2.model

data class QuizQuestion (
    val quizQuestionText: String,
    val quizAnswers: List<QuizAnswer>,
    val correctAnswer: QuizAnswer
)