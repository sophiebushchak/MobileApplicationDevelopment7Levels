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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel7task2.R
import com.example.madlevel7task2.model.Quiz
import com.example.madlevel7task2.vm.QuizViewModel
import kotlinx.android.synthetic.main.fragment_choose_quest.*
import kotlinx.android.synthetic.main.fragment_create_quest.*

const val QUIZ_REQUEST_KEY = "req_quiz"
const val QUIZ_REQUEST_BUNDLE = "bundle_quiz"

class ChooseQuestFragment : Fragment() {
    private val quizzes = arrayListOf<Quiz>()
    private val quizAdapter = QuizAdapter(quizzes, ::onQuizClick)
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_quest, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvChooseQuiz.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvChooseQuiz.adapter = quizAdapter
        observeQuizzes()
        viewModel.getQuiz()
    }

    private fun onQuizClick(quiz: Quiz) {
        setFragmentResult(QUIZ_REQUEST_KEY, bundleOf(Pair(QUIZ_REQUEST_BUNDLE, quiz)))
        findNavController().navigate(R.id.action_chooseQuestFragment_to_welcomeFragment)
    }

    private fun observeQuizzes() {
        viewModel.quizzes.observe(viewLifecycleOwner, Observer {
            quizzes ->
            this@ChooseQuestFragment.quizzes.clear()
            this@ChooseQuestFragment.quizzes.addAll(quizzes)
            quizAdapter.notifyDataSetChanged()
        })
    }

}