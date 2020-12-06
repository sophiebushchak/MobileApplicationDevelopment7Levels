package com.example.madlevel6task2.ui

import android.graphics.Movie
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel6task2.R
import com.example.madlevel6task2.model.MovieResult
import com.example.madlevel6task2.vm.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*

const val MOVIE_REQUEST_KEY = "req_movie"
const val MOVIE_REQUEST_BUNDLE = "bundle_movie"

class MoviesFragment : Fragment() {
    private val movies = arrayListOf<MovieResult>()
    private val moviesAdapter = MoviesAdapter(movies, ::onMovieClick)
    private val viewModel: MoviesViewModel by activityViewModels()

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
        btnSubmit.setOnClickListener {
            onSubmit(etYear.text.toString())
        }
        observeMovies()
        observeErrors()
    }

    private fun onMovieClick(movieResult: MovieResult) {
        setFragmentResult(MOVIE_REQUEST_KEY, bundleOf(Pair(MOVIE_REQUEST_BUNDLE, movieResult)))
        findNavController().navigate(R.id.action_moviesFragment_to_movieDetailFragment)
    }

    private fun observeMovies() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movies ->
            println("Observed movies.")
            this@MoviesFragment.movies.clear()
            this@MoviesFragment.movies.addAll(movies)
            moviesAdapter.notifyDataSetChanged()
        })
    }

    private fun observeErrors() {
        viewModel.errorText.observe(viewLifecycleOwner, Observer {
            error ->
            this.view?.let { Snackbar.make(it, error, Snackbar.LENGTH_SHORT).show() }
        })
    }

    private fun onSubmit(year: String) {
        if (year.isNotBlank()) {
            viewModel.getMovies(year)
        } else {
            Snackbar.make(etYear, "Please enter a year.", Snackbar.LENGTH_SHORT).show()
        }

    }
}