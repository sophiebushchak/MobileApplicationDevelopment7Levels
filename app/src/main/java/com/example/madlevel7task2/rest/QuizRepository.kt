package com.example.madlevel7task2.rest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizQuestion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import java.lang.reflect.GenericArrayType

class QuizRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var quizDocument = firestore.collection("quests").document("quiz")

    private val _quiz: MutableLiveData<Quiz> = MutableLiveData()

    val quiz: LiveData<Quiz>
        get() = _quiz

    private val _createSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val createSucces: LiveData<Boolean> get() = _createSuccess

    suspend fun getQuiz() {
        try {
            withTimeout(5_000) {
                val data = quizDocument
                    .get()
                    .await()
                val quiz = data.toObject(Quiz::class.java)

                _quiz.value = quiz
            }
        } catch (e: Exception) {
            throw QuizRetrievalError(e.message.toString())
        }
    }

    suspend fun createQuiz(quiz: Quiz) {
        try {
            withTimeout(5_000) {
                quizDocument
                    .set(quiz)
                    .await()

                _createSuccess.value = true
            }
        } catch (e: Exception) {
            throw QuizSaveError(e.message.toString(), e)
        }
    }

    class QuizSaveError(message: String, cause: Throwable) : Exception(message, cause)
    class QuizRetrievalError(message: String) : Exception(message)
}