package com.example.madlevel5task2.ui

import android.os.Build
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.item_game.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class BacklogAdapter(private val backlog: List<Game>) : RecyclerView.Adapter<BacklogAdapter.ViewHolder>(){

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return backlog.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(backlog[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun databind(game: Game) {
            itemView.tvGameTitle.text = game.title
            itemView.tvGamePlatforms.text = game.platform
            val formatter = SimpleDateFormat("d MMMM yyyy")
            itemView.tvGameReleaseDate.text = itemView.context.getString(R.string.tvGameReleaseDate,
                formatter.format(game.releaseDate).toString())
        }
    }
}