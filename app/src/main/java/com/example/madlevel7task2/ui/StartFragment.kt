package com.example.madlevel7task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.madlevel7task2.R
import com.example.madlevel7task2.vm.QuizViewModel
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println(viewModel.createSuccess.value)
        initializeButtons()
        observeCreationStatus()
    }

    private fun initializeButtons() {
        btnPlay.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_chooseQuestFragment)
        }
        btnCreate.setOnClickListener {
            viewModel.createQuizzes()
        }
    }

    private fun observeCreationStatus() {
        viewModel.errorText.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.createSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(activity, "Successfully created quizzes.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}