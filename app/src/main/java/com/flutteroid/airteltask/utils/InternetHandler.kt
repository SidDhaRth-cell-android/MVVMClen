package com.flutteroid.airteltask.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class InternetHandler(context: Context) {

  private val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  private val _isConnected = MutableLiveData<Boolean>()
  val isConnected: LiveData<Boolean> = _isConnected

  init {
    checkInitialConnection()
    registerNetworkCallback()
  }

  private fun checkInitialConnection() {
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    _isConnected.postValue(
      networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    )
  }

  private fun registerNetworkCallback() {
    val networkRequest = NetworkRequest.Builder()
      .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      .build()

    connectivityManager.registerNetworkCallback(
      networkRequest,
      object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
          _isConnected.postValue(true)
        }

        override fun onLost(network: Network) {
          _isConnected.postValue(false)
        }
      }
    )
  }
}
