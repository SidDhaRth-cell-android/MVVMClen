package com.flutteroid.airteltask.data.repository

import com.flutteroid.airteltask.data.api.JokesApi
import com.flutteroid.airteltask.data.database.dao.JokesDao
import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.data.model.JokeResponse
import com.flutteroid.airteltask.domain.repository.JokesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class JokesRepositoryImpl @Inject constructor(
  private val api: JokesApi,
  private val dao: JokesDao
) : JokesRepository {
  override suspend fun fetchJokeFromApi(category: String): JokeResponse {
    val response = api.getRandomJoke(category)
    return response
  }

  override suspend fun getPaginatedJokes(page: Int, pageSize: Int): List<JokeEntity> {
    val offset = (page - 1) * pageSize
    return dao.getJokesByPage(pageSize, offset)
  }

  override suspend fun saveJokesToDb(jokes: JokeResponse) {
    val allJokesCount = dao.getJokesCount()
    val jokeEntity =
      JokeEntity(
        jokeId = jokes.id,
        iconUrl = jokes.icon_url,
        categories = jokes.categories.firstOrNull(),
        createdAt = jokes.created_at,
        updatedAt = jokes.updated_at,
        url = jokes.url,
        value = jokes.value,
      )
    if (allJokesCount <= 50) {
      dao.insertJoke(jokeEntity)
    } else {
      dao.deleteOldestJokes()
      dao.insertJoke(jokeEntity)
    }

  }

  override suspend fun getJokesCount(): Int {
    return dao.getJokesCount()
  }
}