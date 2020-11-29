package com.example.madlevel7task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import com.example.madlevel7task2.R
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.model.QuizSession
import com.example.madlevel7task2.vm.QuizSessionViewModel
import com.example.madlevel7task2.vm.QuizViewModel
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.android.synthetic.main.fragment_quiz.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QuizFragment : Fragment() {
    private val quizSessionViewModel: QuizSessionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeQuizSession()
        observeReceivedQuiz()
    }

    private fun setViews(quizSession: QuizSession) {
        tvQuizIndicator.text = quizSession.getQuizTitle()
        tvQuestionIndicator.text = "${quizSession.getCurrentQuestionNumber()}/${quizSession.getTotalQuestionNumber()}"
        tvQuestionText.text = quizSession.getCurrentQuestion().quizQuestionText
    }

    private fun observeReceivedQuiz() {
        setFragmentResultListener(QUIZ_REQUEST_KEY) {_, bundle ->
            bundle.getParcelable<Quiz>(QUIZ_REQUEST_BUNDLE)?.let {
                quizSessionViewModel.startQuizSession(it)
            }
        }
    }

    private fun observeQuizSession() {
        quizSessionViewModel.quizSession.observe(viewLifecycleOwner, Observer {
            setViews(
                it
            )
        })
    }
}