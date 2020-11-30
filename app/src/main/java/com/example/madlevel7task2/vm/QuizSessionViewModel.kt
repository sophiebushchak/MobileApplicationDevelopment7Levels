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
    val quizSession: LiveData<QuizSession> get() = _quizSession
    lateinit var currentSession: QuizSession;

    fun startQuizSession(quiz: Quiz) {
        currentSession = QuizSession(quiz)
        broadcastSessionChanged()
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
        currentSession.advanceCurrentQuestion()
        broadcastSessionChanged()
    }

    private fun broadcastSessionChanged() {
        _quizSession.value = currentSession
    }
}