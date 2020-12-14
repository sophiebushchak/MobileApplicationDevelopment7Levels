package com.example.madlevel6task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.example.madlevel6task2.R
import com.example.madlevel6task2.model.MovieResult
import com.example.madlevel6task2.rest.MovieDBConfig
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import kotlinx.android.synthetic.main.item_movie.*
import kotlinx.android.synthetic.main.item_movie.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMovieSelected()
    }

    private fun observeMovieSelected() {
        setFragmentResultListener(MOVIE_REQUEST_KEY) {_, bundle ->
            bundle.getParcelable<MovieResult>(MOVIE_REQUEST_BUNDLE)?.let {
                setViews(it)
            } ?: Log.e("MovieDetailFragment","Something went wrong with retrieving the Movie parcel.")
        }
    }

    private fun setViews(movie: MovieResult) {
        tvDetailsTitle.text = movie.title
        Glide.with(requireContext()).load(
            MovieDBConfig.IMAGE_BASE_URL + movie.posterPath)
            .into(ivDetailsPoster)
        Glide.with(requireContext()).load(
            MovieDBConfig.IMAGE_BASE_URL + movie.backdropPath
        ).into(ivDetailsBackdrop)
        tvDetailsStarRating.text = movie.voteAverage.toString()
        tvDetailsOverviewText.text = movie.overview
        tvDetailsDate.text = movie.releaseDate
    }
}