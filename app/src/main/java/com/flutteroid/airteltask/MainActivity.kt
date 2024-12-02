package com.flutteroid.airteltask

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.flutteroid.airteltask.presentation.ui.activity.JokesActivity
import com.flutteroid.airteltask.utils.InternetHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var internetHandler: InternetHandler

  override fun onCreate(savedInstanceState: Bundle?) {
    // Use Splash Screen API for Android 12 and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      installSplashScreen()
    }
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    internetHandler = InternetHandler(this)
    internetHandler.isConnected.observe(this) { isConnected ->
      if (isConnected) {
        simulateStartUpDelay()
      } else {
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun simulateStartUpDelay() {
    val intent = Intent(this@MainActivity, JokesActivity::class.java)
    startActivity(intent)
  }
}