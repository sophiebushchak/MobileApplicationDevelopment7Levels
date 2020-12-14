package com.example.madlevel7example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        //always retrieve quiz  when screen is shown
        viewModel.getQuiz()

        btnCreateQuiz.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_createQuizFragment)
        }

        viewModel.quiz.observe(viewLifecycleOwner, Observer {
            //make button visible and clickable
            if (!it.answer.isBlank() || !it.question.isBlank()) {
                btnStartQuiz.alpha = 1.0f
                btnStartQuiz.isClickable = true

                btnStartQuiz.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_quizFragment)
                }
            }
        })
    }
}
