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

    private fun onClickStart(quiz: Quiz) {
        viewModel.startQuizSession(quiz)
        findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
    }

    private fun setViews(quiz: Quiz) {
        tvWelcomeTitle.text = quiz.quizName
        tvWelcomeDescription.text = quiz.quizDescription
        btnStartQuest.setOnClickListener {
            onClickStart(quiz)
        }
    }

    private fun observeQuizSelected() {
        setFragmentResultListener(QUIZ_REQUEST_KEY) { _, bundle ->
            bundle.getParcelable<Quiz>(QUIZ_REQUEST_BUNDLE)?.let {
                val quiz = it
                setViews(quiz)
            } ?: Log.e("WelcomeFragment", "Something went wrong while selecting Quiz.")
        }
    }

    private fun observeQuizFinished() {
        viewModel.sessionOver.observe(viewLifecycleOwner, Observer {
            it?.let {
                setViews(it.quiz)
                Snackbar.make(
                    tvWelcomeTitle,
                    "Completed Quiz ${it.getQuizTitle()} with ${it.countCorrect()} " +
                            "out of ${it.getTotalQuestionNumber()} correct.",
                    500
                ).show()
                viewModel.clearAll()
            } ?: Log.e("WelcomeFragment", "Session is not over.")
        })
    }
}