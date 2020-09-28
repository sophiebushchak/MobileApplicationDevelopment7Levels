package com.example.madlevel6task2.ui

import android.graphics.Movie
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel6task2.R
import com.example.madlevel6task2.model.MovieResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesFragment : Fragment() {
    private val movies = arrayListOf<MovieResult>()
    private val moviesAdapter = MoviesAdapter(movies, ::onMovieClick)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovies.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvMovies.adapter = moviesAdapter
        rvMovies.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun onMovieClick(movieResult: MovieResult) {
        Snackbar.make(rvMovies, "Avengers: Infinity War", Snackbar.LENGTH_LONG).show()
    }
}