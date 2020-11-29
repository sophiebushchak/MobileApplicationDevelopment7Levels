package com.example.madlevel7task2.model

class QuizSession(private val quiz: Quiz) {
    private val answers: MutableMap<QuizQuestion, QuizAnswer?> = mutableMapOf()
    private val currentQuestionIndex: Int = 0

    init {
        for (quizQuestion: QuizQuestion in quiz.getQuestions()) {
            answers[quizQuestion] = null
        }
    }

    fun answerQuestion(quizAnswer: QuizAnswer, quizQuestion: QuizQuestion) {
        if (answers.containsKey(quizQuestion)) {
            println("Updating answer for question: ${quizQuestion.quizQuestionText}")
            answers[quizQuestion] = quizAnswer
        } else {
            println("Inserting answer for question: ${quizQuestion.quizQuestionText}")
            answers[quizQuestion] = quizAnswer
        }
    }

    fun getCurrentQuestion(): QuizQuestion {
        return quiz.getQuestionAtIndex(currentQuestionIndex)
    }

    fun getCurrentQuestionNumber(): Int {
        return this.currentQuestionIndex
    }

    fun getTotalQuestionNumber(): Int {
        return quiz.getTotalQuestions()
    }

    fun countCorrect(): Int {
        var correct: Int = 0
        for (quizQuestion: QuizQuestion in quiz.getQuestions()) {
            if (quizQuestion.correctAnswer == answers[quizQuestion]) {
                correct += 1;
            }
        }
        return correct
    }

    fun getQuizTitle(): String {
        return quiz.quizName;
    }

    fun getQuizDescription(): String {
        return quiz.quizDescription
    }
}

