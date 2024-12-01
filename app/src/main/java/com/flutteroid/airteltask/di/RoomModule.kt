package com.flutteroid.airteltask.di

import android.content.Context
import androidx.room.Room
import com.flutteroid.airteltask.data.database.AppDatabase
import com.flutteroid.airteltask.data.database.dao.JokesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

  @Provides
  @Singleton
  fun provideJokesDao(database: AppDatabase): JokesDao {
    return database.jokesDao() // Provide the DAO instance from your Room database
  }

  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
    return Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      "jokes_database"
    ).build() // Provide the Room database instance
  }

}