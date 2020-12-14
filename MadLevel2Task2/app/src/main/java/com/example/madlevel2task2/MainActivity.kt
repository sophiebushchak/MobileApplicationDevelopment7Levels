package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    /**
     * Adds the QuestionAdapter to the RecyclerView and fills the list with some questions
     * Also adds the item touch helper to the RecyclerView.
     */
    private fun initViews() {
        binding.rvQuestions.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvQuestions.adapter = questionAdapter
        binding.rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        questions.add(Question("A 'val' and 'var' are the same.", false))
        questions.add(Question("Mobile Application Developments grants 12 ECTS.", false))
        questions.add(Question("A Unit in Kotlin corresponds to a void in Java", true))
        questions.add(Question("In Kotlin 'when' replaces the 'switch' operation in Java.", true))
        questions.add(Question("Android 8.0 Oreo is the latest Android version.", false))
        questions.add(Question("Dp stands for density-independent pixels.", true))
        questions.add(Question("View binding is the same as findViewById", false))

        questionAdapter.notifyDataSetChanged()
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    /**
     * Adds an ItemTouchHelper with swipe functionality.
     * A different Snackbar is displayed based on direction swiped and the actual status of the question.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        var callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false;
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition;
                val swipedQuestion: Question = questions[position]
                if (direction == ItemTouchHelper.RIGHT) {
                    if (swipedQuestion.isTrue) {
                        displayCorrectSnackbar(swipedQuestion.isTrue);
                        questions.removeAt(position)
                        questionAdapter.notifyItemRemoved(position)
                    } else {
                        displayIncorrectSnackbar(swipedQuestion.isTrue);
                        questionAdapter.notifyItemChanged(position)
                    }
                } else if (direction == ItemTouchHelper.LEFT) {
                    if (!swipedQuestion.isTrue) {
                        displayCorrectSnackbar(swipedQuestion.isTrue);
                        questions.removeAt(position)
                        questionAdapter.notifyItemRemoved(position)
                    } else {
                        displayIncorrectSnackbar(swipedQuestion.isTrue);
                        questionAdapter.notifyItemChanged(position)
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     * Notifies user of being correct and why.
     */
    private fun displayCorrectSnackbar(questionTruthness: Boolean) {
        var snackbarMessage: String = "You are correct! It's "
        if (questionTruthness) {
            snackbarMessage += "true!"
        } else {
            snackbarMessage += "false!"
        }
        Snackbar.make(binding.root, snackbarMessage, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Notifies user of being incorrect and why.
     */
    private fun displayIncorrectSnackbar(questionTruthness: Boolean) {
        var snackbarMessage: String = "You are incorrect! It's "
        if (questionTruthness) {
            snackbarMessage += "true!"
        } else {
            snackbarMessage += "false!"
        }
        Snackbar.make(binding.root, snackbarMessage, Snackbar.LENGTH_SHORT).show()
    }


}