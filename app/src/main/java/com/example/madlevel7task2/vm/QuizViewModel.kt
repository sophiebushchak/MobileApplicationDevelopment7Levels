package com.example.madlevel7task2.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel7task2.helper.QuizzesWrapper
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.rest.QuizRepository
import com.example.madlevel7task2.tools.MakeExampleQuiz
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "FIRESTORE"
    private val quizRepository: QuizRepository = QuizRepository()

    val quizzes: LiveData<List<Quiz>> = quizRepository.quiz

    val createSuccess: LiveData<Boolean> = quizRepository.createSucces

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String>
        get() = _errorText

    fun getQuiz() {
        viewModelScope.launch {
            try {
                quizRepository.getQuiz()
            } catch (ex: QuizRepository.QuizRetrievalError) {
                val errorMsg = "Something went wrong while retrieving quiz"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
                _errorText.value = null
            }
        }
    }

    fun createQuizzes() {
        val wrapper = QuizzesWrapper()
        wrapper.quizzes.add(MakeExampleQuiz.getAQuiz())
        wrapper.quizzes.add(MakeExampleQuiz.getAnotherQuiz())
        wrapper.quizzes.add(MakeExampleQuiz.getAnotherQuiz())
        wrapper.quizzes.add(MakeExampleQuiz.getAnotherQuiz())
        wrapper.quizzes.add(MakeExampleQuiz.getAnotherQuiz())
        wrapper.quizzes.add(MakeExampleQuiz.getAnotherQuiz())
        viewModelScope.launch {
            try {
                quizRepository.createQuiz(wrapper)
            } catch (ex: QuizRepository.QuizSaveError) {
                val errorMsg = "Something went wrong while creating quizzes."
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }
}