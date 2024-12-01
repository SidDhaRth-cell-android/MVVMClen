package com.flutteroid.airteltask.di

import com.flutteroid.airteltask.data.api.JokesApi
import com.flutteroid.airteltask.data.database.dao.JokesDao
import com.flutteroid.airteltask.data.repository.JokesRepositoryImpl
import com.flutteroid.airteltask.domain.repository.JokesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


  @Provides
  @Singleton
  fun provideJokesRepository(
    jokesApi: JokesApi,
    jokesDao: JokesDao
  ): JokesRepository {
    return JokesRepositoryImpl(jokesApi, jokesDao)
  }
}