package com.flutteroid.airteltask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.data.model.JokeResponse
import com.flutteroid.airteltask.data.model.Resource
import com.flutteroid.airteltask.domain.usecase.FetchJokesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
  private val fetchJokesUseCase: FetchJokesUseCase
) : ViewModel() {

  private val _jokes = MutableStateFlow<List<JokeEntity>>(emptyList())
  val jokes: StateFlow<List<JokeEntity>> = _jokes


  private val _jokeState = MutableStateFlow<Resource<JokeResponse>>(Resource.Loading)
  val jokeState: StateFlow<Resource<JokeResponse>> = _jokeState

  private val _totalJokesCount = MutableStateFlow(0)

  private var currentPage = 1
  private val pageSize = 10

  fun getNewerJokes(category: String) {
    viewModelScope.launch {
      fetchJokesUseCase.fetchJokes(category).collect { resource ->
        _jokeState.value = resource
      }
    }
  }

  fun saveJokesToDb(jokeResponse: JokeResponse?) {
    viewModelScope.launch {
      fetchJokesUseCase.saveJokesToDb(jokeResponse)
    }
  }

  fun getJokes() {
    viewModelScope.launch {
      _totalJokesCount.value = fetchJokesUseCase.getTotalJokesCount()
      _jokes.value = fetchJokesUseCase.getPaginatedJokes(currentPage, pageSize)
    }
  }

  fun loadNextPage() {
    if (_totalJokesCount.value > currentPage * pageSize) {
      currentPage++
      getNewerJokes("music")
    }
  }
}
