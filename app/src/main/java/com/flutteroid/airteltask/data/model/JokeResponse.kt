package com.flutteroid.airteltask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class JokeResponse(
  val categories: List<String>,
  val created_at: String,
  val icon_url: String,
  val id: String,
  val updated_at: String,
  val url: String,
  val value: String
) : Parcelable