package com.example.madlevel6task2.rest

import com.example.madlevel6task2.model.Configuration
import retrofit2.http.GET

interface MovieDBApiService {
    @GET("configuration?api_key={apiKey}")
    suspend fun getConfiguration(apiKey: String): Configuration
}