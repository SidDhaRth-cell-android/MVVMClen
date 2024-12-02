package com.flutteroid.airteltask.domain.usecase

import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.data.model.JokeResponse
import com.flutteroid.airteltask.data.model.Resource
import com.flutteroid.airteltask.domain.repository.JokesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchJokesUseCase @Inject constructor(
  private val jokesRepository: JokesRepository
) {

  suspend fun fetchJokes(category: String): Flow<Resource<JokeResponse>> = flow {
    emit(Resource.Loading)
    val result = jokesRepository.fetchJokeFromApi(category)
    emit(
      when (result.isSuccess) {
        true -> {
          Resource.Success(result.getOrNull()!!)
        }
        false -> Resource.Error(result.exceptionOrNull()?.message.toString())
      }
    )
  }


  suspend fun saveJokesToDb(jokeResponse: JokeResponse?) {
    jokesRepository.saveJokesToDb(jokeResponse)
  }

  suspend fun getPaginatedJokes(currentPage: Int, pageSize: Int): List<JokeEntity> {
    return jokesRepository.getPaginatedJokes(currentPage, pageSize)
  }

  suspend fun getTotalJokesCount(): Int {
    return jokesRepository.getJokesCount()
  }

}