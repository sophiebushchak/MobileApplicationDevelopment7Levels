package com.example.madlevel7example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.lang.Exception

class QuizRepository {
    private var firestore: FirebaseFirestore =
        FirebaseFirestore.getInstance()
    private var quizDocument = firestore
        .collection("quizzes")
        .document("quiz")

    private val _quiz: MutableLiveData<Quiz> = MutableLiveData()

    val quiz: LiveData<Quiz>
        get() = _quiz

    private val _createSucess: MutableLiveData<Boolean> = MutableLiveData()

    val createSuccess: LiveData<Boolean>
        get() = _createSucess

    suspend fun getQuiz() {
        try {
            withTimeout(5_000) {
                val data = quizDocument
                    .get()
                    .await()
                val question = data.getString("question").toString()
                val answer = data.getString("answer").toString()

                _quiz.value = Quiz(question, answer)
            }
        }
        catch (e: Exception) {
            throw QuizRetrievalError("Retrieval-firebase-task was unsuccesful")
        }
    }

    suspend fun createQuiz(quiz: Quiz) {
        try {
            withTimeout(5_000) {
                quizDocument.set(quiz).await()
                _createSucess.value = true
            }
        } catch (e: Exception) {
            throw QuizSaveError(e.message.toString(), e)
        }
    }

    class QuizSaveError(message: String, cause: Throwable) : Exception(message, cause)
    class QuizRetrievalError(message: String) : Exception(message)
}