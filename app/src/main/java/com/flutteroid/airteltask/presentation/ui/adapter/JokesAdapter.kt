package com.flutteroid.airteltask.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flutteroid.airteltask.R
import com.flutteroid.airteltask.data.database.tables.JokeEntity
import com.flutteroid.airteltask.databinding.JokeItemBinding

class JokesAdapter : RecyclerView.Adapter<JokesAdapter.JokeViewHolder>() {
  private val jokes = mutableListOf<JokeEntity>()

  fun updateJokes(newJokes: List<JokeEntity>) {
    jokes.addAll(newJokes)
    jokes.reverse()
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
