package com.flutteroid.airteltask.domain.usecase

import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.domain.repository.JokesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchJokesUseCase @Inject constructor(
  private val jokesRepository: JokesRepository
) {

  suspend fun fetchAndSaveJokes(category: String) {
    val joke = jokesRepository.fetchJokeFromApi(category)
    jokesRepository.saveJokesToDb(joke)
  }

  suspend fun getPaginatedJokes(currentPage: Int, pageSize: Int): List<JokeEntity> {
    return jokesRepository.getPaginatedJokes(currentPage, pageSize)
  }

  suspend fun getTotalJokesCount(): Int {
    return jokesRepository.getJokesCount()
  }

}