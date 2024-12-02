package com.flutteroid.airteltask.presentation.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flutteroid.airteltask.R
import com.flutteroid.airteltask.data.model.Resource
import com.flutteroid.airteltask.databinding.ActivityJokesBinding
import com.flutteroid.airteltask.presentation.ui.adapter.JokesAdapter
import com.flutteroid.airteltask.presentation.viewmodel.JokesViewModel
import com.flutteroid.airteltask.utils.InternetHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JokesActivity : AppCompatActivity() {

  private lateinit var binding: ActivityJokesBinding
  private val jokesViewModel: JokesViewModel by viewModels()
  private lateinit var internetHandler: InternetHandler
  private val jokesAdapter by lazy {
    JokesAdapter()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityJokesBinding.inflate(layoutInflater)
    setContentView(binding.root)
    internetHandler = InternetHandler(this)
    configureToolbar() // Set up the toolbar
    configureRecyclerView()  // Set up RecyclerView for displaying jokes
    getDataFromViewModel() // Observe and fetch jokes data from ViewModel
    observerJokes() // Observe changes in the jokes and state
  }

  /**
   * Configures the RecyclerView to display a list of jokes and adds a scroll listener
   * to handle pagination by loading more data when the user scrolls to the last item.
   */
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

  /**
   * Observes the jokes data and updates the RecyclerView with new jokes.
   * Also, handles empty joke lists by fetching newer jokes.
   */
  private fun observerJokes() {
    lifecycleScope.launch {
      jokesViewModel.jokes.collect { jokeList ->
        if (jokeList.isNotEmpty()) {
          jokesAdapter.updateJokes(jokeList)
          binding.jokesList.adapter = jokesAdapter
        } else {
          jokesViewModel.getNewerJokes("music")
        }
      }
    }

    lifecycleScope.launch {
      jokesViewModel.jokeState.collect { resource ->
        when (resource) {
          is Resource.Loading -> {
            showLoader()
          }

          is Resource.Success -> {
            // Handle success and update UI
            hideLoader()
            jokesViewModel.saveJokesToDb(resource.data)
            delay(1000) // Introduce delay before getting updated jokes
            jokesViewModel.getJokes()
          }

          is Resource.Error -> {
            // Handle error and show a message
            hideLoader()
            Toast.makeText(this@JokesActivity, resource.message, Toast.LENGTH_SHORT).show()
          }
        }
      }
    }
  }

  /**
   * Observes the internet connection status and fetches jokes from the ViewModel
   * if there is an internet connection. Otherwise, shows a Toast message.
   */
  private fun getDataFromViewModel() {
    internetHandler.isConnected.observe(this) { isConnected ->
      if (isConnected) {
        showLoader()
        jokesViewModel.getJokes()
      } else {
        Toast.makeText(this, getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show()
      }
    }
  }


  /**
   * Shows a loading indicator in the UI.
   */
  private fun showLoader() {
    binding.loader.isVisible = true
  }

  /**
   * Hides the loading indicator in the UI.
   */
  private fun hideLoader() {
    binding.loader.isVisible = false
  }

  /**
   * Configures the toolbar with a title and sets a click listener on the refresh button
   * to fetch newer jokes when clicked.
   */
  private fun configureToolbar() {
    binding.toolbar.title = getString(R.string.jokes)
    binding.refreshButton.setOnClickListener {
      showLoader()
      jokesViewModel.getNewerJokes("music")
    }
  }
}