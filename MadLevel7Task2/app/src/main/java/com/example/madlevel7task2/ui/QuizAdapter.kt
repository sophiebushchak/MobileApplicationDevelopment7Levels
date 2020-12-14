package com.example.madlevel7task2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel7task2.R
import com.example.madlevel7task2.model.Quiz
import kotlinx.android.synthetic.main.item_quest.view.*

class QuizAdapter(private val quests: List<Quiz>, private val onClick: (Quiz) -> Unit) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_quest, parent, false)
        )
    }

    override fun getItemCount(): Int = quests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(quests[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(quests[adapterPosition]) }
        }

        fun bind(quiz: Quiz) {
            itemView.tvQuestTitle.text = quiz.quizName
            itemView.tvQuestDescription.text = quiz.quizDescription
        }
    }

}