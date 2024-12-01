package com.flutteroid.airteltask.data.database.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val jokeId: String? = null,
  val categories: String? = null,
  val createdAt: String? = null,
  val iconUrl: String? = null,
  val updatedAt: String? = null,
  val url: String? = null,
  val value: String? = null
)