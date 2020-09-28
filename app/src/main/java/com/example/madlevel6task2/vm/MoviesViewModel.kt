package com.example.madlevel6task2.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.madlevel6task2.rest.MovieDBRepository
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val movieDBRepository = MovieDBRepository()

    val movies = movieDBRepository.movies

    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    fun getMovies(year: String) {
        viewModelScope.launch {
            try {
                movieDBRepository.getMoviesForYear(year.toInt())
                println("Requested for movieDBRepository to retrieve movies.")
        } catch (error: MovieDBRepository.TriviaRefreshError) {
                _errorText.value = error.message
                Log.e("Error", error.cause.toString())
            }
        }
    }
}