package com.example.madlevel7task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.R
import com.example.madlevel7task2.vm.QuizSessionViewModel
import com.example.madlevel7task2.vm.QuizViewModel
import kotlinx.android.synthetic.main.fragment_welcome.*

const val QUIZ_REQUEST_KEY = "req_quiz"
const val QUIZ_REQUEST_BUNDLE = "bundle_quiz"
class WelcomeFragment : Fragment() {
    private val quizViewModel: QuizViewModel by activityViewModels()
    private val quizSessionViewModel: QuizSessionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        quizViewModel.createQuizzes()
        quizViewModel.getQuiz()
        observeQuiz()
        btnStartQuest.setOnClickListener {
            onClickStart()
        }
    }

    private fun onClickStart() {
        findNavController().navigate(R.id.action_welcomeFragment_to_quizFragment)
    }

    private fun observeQuiz() {
        quizViewModel.quiz.observe(viewLifecycleOwner, Observer {
            val quiz = it[0]
            quizSessionViewModel.startQuizSession(it[0])
            tvWelcomeTitle.text = quiz.quizName
            tvWelcomeDescription.text = quiz.quizDescription
        })
    }
}