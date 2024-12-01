package com.flutteroid.airteltask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flutteroid.airteltask.data.database.tables.JokeEntity
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

  private val _totalJokesCount = MutableStateFlow(0)
  val totalJokesCount: StateFlow<Int> = _totalJokesCount

  private var currentPage = 1
  private val pageSize = 10

  fun getJokes(category: String) {
    viewModelScope.launch {
      fetchJokesUseCase.fetchAndSaveJokes(category)
      _totalJokesCount.value = fetchJokesUseCase.getTotalJokesCount()
      _jokes.value = fetchJokesUseCase.getPaginatedJokes(currentPage, pageSize)
    }
  }

  fun loadNextPage() {
    if (_totalJokesCount.value > currentPage * pageSize) {
      currentPage++
      getJokes("music")
    }
  }
}
