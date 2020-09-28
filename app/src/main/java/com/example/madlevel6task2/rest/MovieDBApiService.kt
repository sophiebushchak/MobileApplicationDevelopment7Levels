package com.example.madlevel6task2.rest

import com.example.madlevel6task2.model.MovieResponse
 import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBApiService {
    @GET("3/discover/movie?language=en-US&sort_by=popularity.desc&include_video=false")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String, @Query("year") year: Int): MovieResponse

}