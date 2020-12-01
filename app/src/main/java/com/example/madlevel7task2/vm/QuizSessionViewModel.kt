package com.example.madlevel7task2.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizAnswer
import com.example.madlevel7task2.model.QuizSession

class QuizSessionViewModel(application: Application) : AndroidViewModel(application) {
    private val _quizSession: MutableLiveData<QuizSession> = MutableLiveData()
    private val _sessionOver: MutableLiveData<QuizSession> = MutableLiveData()
    private val _error: MutableLiveData<String> = MutableLiveData()
    val sessionOver: LiveData<QuizSession> get() = _sessionOver
    val quizSession: LiveData<QuizSession> get() = _quizSession
    val error: LiveData<String> get() = _error
    private var currentSession: QuizSession? = null

    fun startQuizSession(quiz: Quiz) {
        _sessionOver.value = null
        currentSession = QuizSession(quiz)
        broadcastSession()
    }

    fun answerQuestion(answerText: String) {
        if (answerText.isBlank()) {
            broadcastError("Please fill in an answer.")
        } else {
            val quizAnswer = QuizAnswer(answerText)
            currentSession?.let {
                if (!it.getCurrentQuestion().quizAnswers.contains(quizAnswer)) {
                    broadcastError("Something went wrong.")
                }
                it.answerQuestion(quizAnswer, it.getCurrentQuestion())
                if (it.getCurrentQuestionNumber() == it.getTotalQuestionNumber()) {
                    finishSession()
                } else {
                    it.advanceCurrentQuestion()
                    broadcastSession()
                }
            } ?: broadcastError("Attempted to answer a question without a quiz session.")
        }
    }

    fun decrementQuestion() {
        currentSession?.let{
            it.decrementCurrentQuestion()
            broadcastSession()
        } ?: broadcastError("Something went wrong.")
    }

    fun isOnFirstQuestion(): Boolean {
        return if (currentSession != null) {
            (currentSession!!.getCurrentQuestionNumber() == 1)
        } else {
            false;
        }
    }

    fun clearAll() {
        _quizSession.value = null
        _sessionOver.value = null
    }

    fun finishSession() {
        _sessionOver.value = currentSession
    }

    private fun broadcastSession() {
        _quizSession.value = currentSession
        println(currentSession)
    }

    private fun broadcastError(message: String) {
        _error.value = message
    }
}