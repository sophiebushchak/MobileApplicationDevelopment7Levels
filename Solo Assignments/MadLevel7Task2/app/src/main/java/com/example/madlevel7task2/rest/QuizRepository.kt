package com.example.madlevel7task2.rest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.helper.QuizzesWrapper
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizQuestion
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import java.lang.Exception
import java.lang.reflect.GenericArrayType

/**
 * The repository that connects to Firebase to get the quizzes.
 * When it retrieves the quizzes from the document, it converts them to the QuizzesWrapper class.
 * The reason is that otherwise it would have to be converted to a List or Map.
 * But a List or Map is generic and the Android Firebase library currently does not support
 * something that is normally needed to map a document's data to a generic class.
 */
class QuizRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var quizDocument = firestore.collection("quests2").document("quizzes")

    private val _quiz: MutableLiveData<List<Quiz>> = MutableLiveData()

    val quiz: LiveData<List<Quiz>>
        get() = _quiz

    private val _createSuccess: MutableLiveData<Boolean> = MutableLiveData()

    val createSucces: LiveData<Boolean> get() = _createSuccess

    suspend fun getQuiz() {
        try {
            withTimeout(5_000) {
                val data = quizDocument
                    .get()
                    .await()
                val quizzes = data.toObject(QuizzesWrapper::class.java)

                if (quizzes != null) {
                    _quiz.value = quizzes.quizzes
                }
            }
        } catch (e: Exception) {
            throw QuizRetrievalError("Get Error:" + e.message.toString())
        }
    }

    suspend fun createQuiz(quizzes: QuizzesWrapper) {
        try {
            withTimeout(5_000) {
                quizDocument
                    .set(quizzes)
                    .await()

                _createSuccess.value = true
                _createSuccess.value = false
            }
        } catch (e: Exception) {
            throw QuizSaveError("Create Error: " + e.message.toString(), e)
        }
    }

    class QuizSaveError(message: String, cause: Throwable) : Exception(message, cause)
    class QuizRetrievalError(message: String) : Exception(message)
}