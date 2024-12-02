package com.flutteroid.airteltask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flutteroid.airteltask.R
import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.databinding.JokeItemBinding


/**
 * Adapter class to bind a list of jokes to a RecyclerView.
 *
 * This adapter handles displaying jokes in a list and ensures that newly fetched jokes
 * are displayed at the top while avoiding duplicates based on joke IDs.
 */
class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokeViewHolder>() {
  private val jokes = mutableListOf<JokeEntity>()
  private val jokeIds = mutableSetOf<Long>() // Track unique IDs


  /**
   * Updates the jokes list by adding new jokes at the top.
   * Only jokes that haven't been added already (based on their unique ID) will be included.
   *
   * @param newJokes List of new jokes to be added.
   */
  fun updateJokes(newJokes: List<JokeEntity>) {
    val filteredJokes = newJokes.filter { joke -> !jokeIds.contains(joke.id) }
    jokeIds.addAll(filteredJokes.map { it.id })
    jokes.addAll(0, filteredJokes) // Add new jokes at the top
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
    val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return JokeViewHolder(binding)
  }

  override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
    holder.bind(jokes[position])
  }

  override fun getItemCount() = jokes.size


  /**
   * Binds a JokeEntity to the view components in the ViewHolder.
   *
   * @param joke The JokeEntity to bind to the view.
   */
  inner class JokeViewHolder(private val binding: JokeItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(joke: JokeEntity) {
      binding.jokeText.text = joke.value
      binding.categories.text = joke.categories ?: "N/A"
      Glide.with(binding.root.context).load(joke.iconUrl).error(R.drawable.ic_launcher_foreground)
        .into(binding.iconImage)
    }
  }
}
