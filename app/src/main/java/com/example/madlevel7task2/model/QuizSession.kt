package com.example.madlevel7task2.model

class QuizSession(val quiz: Quiz) {
    private val answers: MutableMap<QuizQuestion, QuizAnswer?> = mutableMapOf()
    private var currentQuestionIndex: Int = 0

    init {
        for (quizQuestion: QuizQuestion in quiz.getQuestions()) {
            answers[quizQuestion] = null
        }
    }

    fun answerQuestion(quizAnswer: QuizAnswer, quizQuestion: QuizQuestion) {
        println("Answer for question: ${quizQuestion.quizQuestionText} = ${quizAnswer.answerText}")
        answers[quizQuestion] = quizAnswer
        println(countCorrect())
    }

    fun decrementCurrentQuestion() {
        if (this.currentQuestionIndex != 0) {
            this.currentQuestionIndex -= 1
        }
    }

    fun advanceCurrentQuestion() {
        if (this.currentQuestionIndex < this.quiz.quizQuestions.size - 1) {
            this.currentQuestionIndex += 1
        }
    }

    fun getCurrentQuestion(): QuizQuestion {
        return quiz.getQuestionAtIndex(currentQuestionIndex)
    }

    fun getCurrentQuestionNumber(): Int {
        return this.currentQuestionIndex + 1
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

    override fun toString(): String {
        return "QuizSession with current question: ${getCurrentQuestionNumber()}\nWith correct: ${countCorrect()}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuizSession

        if (quiz != other.quiz) return false
        if (answers != other.answers) return false
        if (currentQuestionIndex != other.currentQuestionIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = quiz.hashCode()
        result = 31 * result + answers.hashCode()
        result = 31 * result + currentQuestionIndex
        return result
    }
}

