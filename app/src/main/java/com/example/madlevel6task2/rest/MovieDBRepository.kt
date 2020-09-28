package com.example.madlevel6task2.rest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel6task2.model.MovieResult
import kotlinx.coroutines.withTimeout

class MovieDBRepository {
    private val movieDBApiService: MovieDBApiService = MovieDBApi.createApi()
    private val _movies: MutableLiveData<List<MovieResult>> = MutableLiveData()

    /**
     *
     */
    val movies: MutableLiveData<List<MovieResult>>
        get() = _movies

    /**
     *
     */
    suspend fun getMoviesForYear(year: Int) {
        try {
            val result = withTimeout(5_000) {
                movieDBApiService.getPopularMovies(MovieDBConfig.API_KEY, year)
            }

            _movies.value = result.results
        } catch (error: Throwable) {
            throw TriviaRefreshError("Unable to refresh trivia", error)
        }
    }

    class TriviaRefreshError(message: String, cause: Throwable) : Throwable(message, cause)

}