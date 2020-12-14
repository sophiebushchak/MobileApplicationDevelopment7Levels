package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.model.Game
import kotlinx.android.synthetic.main.item_game_history_result.view.*
import java.util.*
import kotlin.collections.ArrayList

class GameHistoryAdapter(private val games: ArrayList<Game>) :
    RecyclerView.Adapter<GameHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game_history_result, parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(game: Game) {
            itemView.tvHistoryDate.text = game.date.toString()
            //Make first letter uppercase
            itemView.tvHistoryResult.text = game.result.toString().substring(0, 1) +
                    game.result.toString().toLowerCase(Locale.US).substring(1)
            itemView.ivHistoryPlayerThrow.setImageResource(Game.getThrowImage(game.playerThrow))
            itemView.ivHistoryOpponentThrow.setImageResource(Game.getThrowImage(game.opponentThrow))
        }
    }
}