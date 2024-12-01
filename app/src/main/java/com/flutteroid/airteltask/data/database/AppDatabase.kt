package com.flutteroid.airteltask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flutteroid.airteltask.data.database.dao.JokesDao
import com.flutteroid.airteltask.data.database.tables.JokeEntity

@Database(entities = [JokeEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
  abstract fun jokesDao(): JokesDao

}