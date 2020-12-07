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
 * This fragment holds the UI for playing a quiz.
 */
class QuizFragment : Fragment() {
    private val viewModel: QuizSessionViewModel by activityViewModels()

    /**
     * Regular onCreateView method, except for that a callback is also made on the onBackPressedDispatcher.
     * This allows the quiz session to go back a question if the current question is not the first question.
     * Alternatively it finishes the quiz session prematurely if the user is on the first question and goes back.
     */
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

    /**
     * Sets the click listener on the confirm answer button.
     */
    private fun initializeViews() {
        btnConfirmAnswer.setOnClickListener {
            confirmAnswer()
        }
    }

    /**
     * Gets the currently checked radio button. If there is none, answers the quiz with a blank answer.
     */
    private fun confirmAnswer() {
        val rbChosenId = answerOptionsRadioGroup.checkedRadioButtonId
        val rb: RadioButton? = answerOptionsRadioGroup.findViewById(rbChosenId)
        rb?.let {
            viewModel.answerQuestion(it.text.toString())
        } ?: viewModel.answerQuestion("")
    }

    /**
     * Updates the UI to display the current question and quiz session information.
     */
    private fun updateViews(quizSession: QuizSession) {
        tvQuizIndicator.text = quizSession.getQuizTitle()
        tvQuestionIndicator.text = getString(R.string.tvQuestionIndicator,
            quizSession.getCurrentQuestionNumber(),
            quizSession.getTotalQuestionNumber())
        tvQuestionText.text = quizSession.getCurrentQuestion().quizQuestionText
        fillRadioGroup(quizSession)
   }

    /**
     * Fills the radio group with quiz answers.
     */
    private fun fillRadioGroup(quizSession: QuizSession) {
        val radioGroup = answerOptionsRadioGroup
        radioGroup.removeAllViews()
        radioGroup.clearCheck()
        var radioButtonIncrements = 0
        for (quizAnswer in quizSession.getCurrentQuestion().quizAnswers) {
            val rb = RadioButton(requireContext());
            rb.text = quizAnswer.answerText
            rb.id = radioButtonIncrements
            //If a user navigates back and forth in the quiz, check the previously chosen answer.
            if (quizAnswer == viewModel.getPreviousAnswerForCurrentQuestion()) {
                rb.isChecked = true
            }
            radioGroup.addView(rb)
            radioButtonIncrements += 1
        }
    }

    /**
     * Observes some live values.
     */
    private fun observeQuizSession() {
        //Pass the current quizsession with its state to the updateViews method.
        viewModel.quizSession.observe(viewLifecycleOwner, Observer {
            it?.let {
                updateViews(
                    it
                )
            }
        })
        //Ends the session if the viewmodel emits a non-null value for sessionOver.
        viewModel.sessionOver.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().popBackStack()
            }
        })
        //Displays an error if one is made.
        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let{
                Snackbar.make(answerOptionsRadioGroup, it, 500).show()
            }
        })
    }
}