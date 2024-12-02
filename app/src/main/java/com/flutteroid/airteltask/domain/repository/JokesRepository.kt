package com.flutteroid.airteltask.domain.repository

import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.data.model.JokeResponse
import kotlinx.coroutines.flow.Flow

interface JokesRepository {
  suspend fun fetchJokeFromApi(category: String): Result<JokeResponse>
  suspend fun getPaginatedJokes(page: Int, pageSize: Int): List<JokeEntity>
  suspend fun saveJokesToDb(jokes: JokeResponse?)
  suspend fun getJokesCount(): Int
}