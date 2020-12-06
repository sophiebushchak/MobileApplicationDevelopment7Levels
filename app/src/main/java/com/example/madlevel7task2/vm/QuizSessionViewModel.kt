package com.example.madlevel7task2.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizAnswer
import com.example.madlevel7task2.model.QuizQuestion
import com.example.madlevel7task2.model.QuizSession

class QuizSessionViewModel(application: Application) : AndroidViewModel(application) {
    private val _quizSession: MutableLiveData<QuizSession> = MutableLiveData()
    private val _sessionOver: MutableLiveData<QuizSession> = MutableLiveData()
    private val _error: MutableLiveData<String> = MutableLiveData()
    val sessionOver: LiveData<QuizSession> get() = _sessionOver
    val quizSession: LiveData<QuizSession> get() = _quizSession
    val error: LiveData<String> get() = _error
    private var currentSession: QuizSession? = null

    /**
     * Resets all the live data values, and then starts a new quiz session.
     */
    fun startQuizSession(quiz: Quiz) {
        _sessionOver.value = null
        _error.value = null
        currentSession = QuizSession(quiz)
        broadcastSession()
    }

    /**
     * Answers a quiz question with an answer.
     * If the last question was answered, finish the session.
     */
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

    /**
     * Go back a question.
     */
    fun decrementQuestion() {
        currentSession?.let{
            it.decrementCurrentQuestion()
            broadcastSession()
        } ?: broadcastError("Something went wrong.")
    }

    /**
     * Check if the quiz session is currently on the first question.
     */
    fun isOnFirstQuestion(): Boolean {
        return if (currentSession != null) {
            (currentSession!!.getCurrentQuestionNumber() == 1)
        } else {
            false;
        }
    }

    /**
     * Gets the previous answer given for the current question.
     */
    fun getPreviousAnswerForCurrentQuestion(): QuizAnswer? {
        currentSession?.let {
            return it.getAnswerForQuestion(it.getCurrentQuestion())
        } ?: return null
    }

    /**
     * Clears all livedata values.
     */
    fun clearAll() {
        _error.value = null
        _quizSession.value = null
        _sessionOver.value = null
    }

    /**
     * Finishes the session.
     */
    fun finishSession() {
        _sessionOver.value = currentSession
    }

    /**
     * Emit the [_quizSession] livedata with the [currentSession] as the session has important values
     * that change once actions are performed.
     */
    private fun broadcastSession() {
        _quizSession.value = currentSession
        println(currentSession)
    }

    /**
     * Emits a message through the [_error] livedata.
     */
    private fun broadcastError(message: String) {
        _error.value = message
    }
}