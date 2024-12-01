package com.flutteroid.airteltask.di

import com.flutteroid.airteltask.data.api.JokesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {


  @Provides
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .addInterceptor(HttpLoggingInterceptor()).build()
  }

  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .add(KotlinJsonAdapterFactory())  // Add support for Kotlin classes
      .build()
  }


  @Provides
  fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
      .baseUrl("https://api.chucknorris.io/")  // Replace with your actual base URL
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))  // Using Moshi for JSON conversion
      .build()
  }

  @Provides
  fun provideApiService(retrofit: Retrofit): JokesApi {
    return retrofit.create(JokesApi::class.java)
  }
}