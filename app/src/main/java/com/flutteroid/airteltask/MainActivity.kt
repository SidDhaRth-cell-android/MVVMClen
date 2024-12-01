package com.flutteroid.airteltask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.flutteroid.airteltask.presentation.ui.activity.JokesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    // initialize Splash Screen
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)
    simulateStartUpDelay()
  }

  private fun simulateStartUpDelay() {
    val intent = Intent(this@MainActivity, JokesActivity::class.java)
    startActivity(intent)
  }
}