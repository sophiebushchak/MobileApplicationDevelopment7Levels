package com.example.madlevel7task2.model

data class Quiz (
    val quizName: String,
    val quizDescription: String,
    val quizQuestions: List<QuizQuestion>
)