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
    val sessionOver: LiveData<QuizSession> get() = _sessionOver
    val quizSession: LiveData<QuizSession> get() = _quizSession
    private lateinit var currentSession: QuizSession

    fun startQuizSession(quiz: Quiz) {
        _sessionOver.value = null
        currentSession = QuizSession(quiz)
        broadcastSession()
    }

    fun answerQuestion(answerText: String) {
        var quizAnswer: QuizAnswer? = null
        for (q in currentSession.getCurrentQuestion().quizAnswers) {
            if (q.answerText == answerText) {
                quizAnswer = QuizAnswer(answerText)
            }
        }
        if (quizAnswer != null) {
            currentSession.answerQuestion(quizAnswer, currentSession.getCurrentQuestion())
        }
        if (currentSession.getCurrentQuestionNumber() == currentSession.getTotalQuestionNumber()) {
            finishSession()

        } else {
            currentSession.advanceCurrentQuestion()
            broadcastSession()
        }

    }

    fun clearAll() {
        _quizSession.value = null
        _sessionOver.value = null
    }

    private fun finishSession() {
        _sessionOver.value = currentSession
    }

    private fun broadcastSession() {
        _quizSession.value = currentSession
        println(currentSession)
    }
}