package com.example.madlevel6task2.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.madlevel6task2.R
import com.example.madlevel6task2.model.MovieResult
import com.example.madlevel6task2.rest.MovieDBConfig
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesAdapter(private val movies: List<MovieResult>, private val onClick: (MovieResult) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener { onClick(movies[adapterPosition]) }
        }

        fun bind(movie: MovieResult) {
            Glide.with(context).load(MovieDBConfig.IMAGE_BASE_URL + movie.posterPath).into(itemView.ivImagePoster)
            itemView.tvMovieTitle.text = movie.title
            itemView.tvPosition.text = (movies.indexOf(movie) + 1).toString() + "."
        }
    }

}