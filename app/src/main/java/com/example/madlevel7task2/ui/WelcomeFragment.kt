package com.example.madlevel7task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.R
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.vm.QuizSessionViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_welcome.*

class WelcomeFragment : Fragment() {
    private val viewModel: QuizSessionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeQuizSelected()
        observeQuizFinished()
    }

    /**
     * Starts the quiz session with the passed quiz.
     */
    private fun onClickStart(quiz: Quiz) {
        viewModel.startQuizSession(quiz)
        findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
    }

    /**
     * Updates the UI with information about the selected quiz. It also sets a click listener
     * on the start quest button by passing the quiz parameter into [onClickStart].
     * This allows the button to start the quiz previously selected in [ChooseQuestFragment]
     */
    private fun setViews(quiz: Quiz) {
        tvWelcomeTitle.text = quiz.quizName
        tvWelcomeDescription.text = quiz.quizDescription
        btnStartQuest.setOnClickListener {
            onClickStart(quiz)
        }
    }

    /**
     * Receives the quiz fragment result from [ChooseQuestFragment]
     */
    private fun observeQuizSelected() {
        setFragmentResultListener(QUIZ_REQUEST_KEY) { _, bundle ->
            bundle.getParcelable<Quiz>(QUIZ_REQUEST_BUNDLE)?.let {
                val quiz = it
                setViews(quiz)
            } ?: Log.e("WelcomeFragment", "Something went wrong while selecting Quiz.")
        }
    }

    /**
     * This observer is important for when a quiz session is finished.
     * [QuizSessionViewModel.sessionOver] holds a value of a quizsession that was played.
     * This is used to both display a snackbar with the amount of questions correct, and to
     * update the UI once more with the quiz inside if this fragment is navigated to by popping
     * the backstack.
     */
    private fun observeQuizFinished() {
        viewModel.sessionOver.observe(viewLifecycleOwner, Observer {
            it?.let {
                setViews(it.getQuiz())
                Snackbar.make(
                    tvWelcomeTitle,
                    getString(R.string.snackbarComplete, it.getQuizTitle(), it.countCorrect(), it.getTotalQuestionNumber()),
                    1500
                ).show()
                viewModel.clearAll()
            }
        })
    }
}