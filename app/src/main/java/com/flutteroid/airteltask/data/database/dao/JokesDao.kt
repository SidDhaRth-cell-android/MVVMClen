package com.flutteroid.airteltask.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.flutteroid.airteltask.data.database.tables.JokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDao {
  @Query("SELECT * FROM jokes LIMIT :pageSize OFFSET :offset")
  suspend fun getJokesByPage(pageSize: Int, offset: Int): List<JokeEntity>

  @Query("SELECT COUNT(*) FROM jokes")
  suspend fun getJokesCount(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertJoke(jokes: JokeEntity)

  @Query("DELETE FROM jokes")
  suspend fun deleteOldestJokes()
}