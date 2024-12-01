package com.flutteroid.airteltask.data.api

import com.flutteroid.airteltask.data.model.JokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {
  @GET("jokes/random")
  suspend fun getRandomJoke(@Query("category") category: String): JokeResponse
}