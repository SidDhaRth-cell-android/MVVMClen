package com.flutteroid.airteltask.presentation.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flutteroid.airteltask.databinding.ActivityJokesBinding
import com.flutteroid.airteltask.presentation.ui.adapter.JokesAdapter
import com.flutteroid.airteltask.presentation.viewmodel.JokesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JokesActivity : AppCompatActivity() {

  private lateinit var binding: ActivityJokesBinding
  private val jokesViewModel: JokesViewModel by viewModels()
  private val jokesAdapter by lazy {
    JokesAdapter()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityJokesBinding.inflate(layoutInflater)
    setContentView(binding.root)
    configureToolbar()
    configureRecyclerView()
    getDataFromViewModel()
    observerJokes()
  }

  private fun configureRecyclerView() {
    binding.jokesList.layoutManager = LinearLayoutManager(this)
    binding.jokesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // Check if it's the last item and load more data
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
        val itemCount = layoutManager.itemCount

        if (lastVisibleItem == itemCount - 1) {
          jokesViewModel.loadNextPage()
        }
      }
    })
  }

  private fun observerJokes() {
    lifecycleScope.launch {
      jokesViewModel.jokes.collect { jokeList ->
        hideLoader()
        Log.e("TAG", "observerJokes: $jokeList")
        if (jokeList.isNotEmpty()) {
          jokesAdapter.updateJokes(jokeList)
          binding.jokesList.adapter = jokesAdapter
        } else {
          // NO JOKE AVAILABLE
        }
      }
    }
  }

  private fun getDataFromViewModel() {
    showLoader()
    jokesViewModel.getJokes("music")
  }


  private fun showLoader() {
    binding.loader.isVisible = true
  }

  private fun hideLoader() {
    binding.loader.isVisible = false
  }

  private fun configureToolbar() {
    binding.toolbar.title = "Jokes"
    binding.refreshButton.setOnClickListener {
      getDataFromViewModel()
    }
  }
}