package com.example.madlevel7task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.core.view.iterator
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.R
import com.example.madlevel7task2.model.QuizSession
import com.example.madlevel7task2.vm.QuizSessionViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_quiz.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QuizFragment : Fragment() {
    private val viewModel: QuizSessionViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (viewModel.isOnFirstQuestion()) {
                viewModel.finishSession()
            } else {
                viewModel.decrementQuestion()
            }
        }
        callback.isEnabled = true
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
    }

    private fun confirmAnswer() {
        val rbChosenId = answerOptionsRadioGroup.checkedRadioButtonId
        val rb: RadioButton? = answerOptionsRadioGroup.findViewById(rbChosenId)
        rb?.let {
            viewModel.answerQuestion(it.text.toString())
        } ?: viewModel.answerQuestion("")
        answerOptionsRadioGroup.clearCheck()
    }

    private fun updateViews(quizSession: QuizSession) {
        tvQuizIndicator.text = quizSession.getQuizTitle()
        tvQuestionIndicator.text = "${quizSession.getCurrentQuestionNumber()}/${quizSession.getTotalQuestionNumber()}"
        tvQuestionText.text = quizSession.getCurrentQuestion().quizQuestionText
        fillRadioGroup(quizSession)
   }

    private fun fillRadioGroup(quizSession: QuizSession) {
        val radioGroup = answerOptionsRadioGroup
        radioGroup.removeAllViews()
        var radioButtonIncrements = 0
        for (quizAnswer in quizSession.getCurrentQuestion().quizAnswers) {
            val rb = RadioButton(requireContext());
            rb.text = quizAnswer.answerText
            rb.id = radioButtonIncrements
            if (quizAnswer == viewModel.getPreviousAnswerForCurrentQuestion()) {
                rb.isChecked = true
            }
            radioGroup.addView(rb)
            radioButtonIncrements += 1
        }
    }

    private fun observeQuizSession() {
        viewModel.quizSession.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateViews(
                    it
                )
            }
        })
        viewModel.sessionOver.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().popBackStack()
            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let{
                Snackbar.make(answerOptionsRadioGroup, it, 500).show()
            }
        })
    }
}