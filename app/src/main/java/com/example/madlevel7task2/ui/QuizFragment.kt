package com.example.madlevel7task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        initializeViews()
    }

    private fun initializeViews() {
        btnConfirmAnswer.setOnClickListener {
            confirmAnswer()
        }
        answerOptionsRadioGroup.check(0)
    }

    private fun confirmAnswer() {
        val rbChosenId = answerOptionsRadioGroup.checkedRadioButtonId
        val rb: RadioButton = answerOptionsRadioGroup.findViewById(rbChosenId)
        quizSessionViewModel.answerQuestion(rb.text.toString())
    }

    private fun updateViews(quizSession: QuizSession) {
        tvQuizIndicator.text = quizSession.getQuizTitle()
        tvQuestionIndicator.text = "${quizSession.getCurrentQuestionNumber()}/${quizSession.getTotalQuestionNumber()}"
        tvQuestionText.text = quizSession.getCurrentQuestion().quizQuestionText
        fillRadioGroup(quizSession);
    }

    private fun fillRadioGroup(quizSession: QuizSession) {
        val radioGroup = answerOptionsRadioGroup
        radioGroup.removeAllViews()
        var radioButtonIncrements = 0
        for (quizAnswer in quizSession.getCurrentQuestion().quizAnswers) {
            val rb = RadioButton(requireContext());
            rb.text = quizAnswer.answerText
            rb.id = radioButtonIncrements
            radioGroup.addView(rb)
            radioButtonIncrements += 1
        }
    }

    private fun observeQuizSession() {
        quizSessionViewModel.quizSession.observe(viewLifecycleOwner, Observer {
            updateViews(
                it
            )
        })
        quizSessionViewModel.sessionOver.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
            }
        })
    }
}