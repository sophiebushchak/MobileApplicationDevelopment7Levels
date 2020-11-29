package com.example.madlevel7task2.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizSession

class QuizSessionViewModel(application: Application) : AndroidViewModel(application) {
    private val _quizSession: MutableLiveData<QuizSession> = MutableLiveData()
    val quizSession: LiveData<QuizSession> get() = _quizSession

    fun startQuizSession(quiz: Quiz) {
        _quizSession.value = QuizSession(quiz)
    }
}