package com.fxapp.libfoundation.view.base

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = getColor(com.fxapp.libfoundation.R.color.black),
                darkScrim = getColor(com.fxapp.libfoundation.R.color.black),
                detectDarkMode = ::isDarkMode
            )
        )
        super.onCreate(savedInstanceState)
    }

    fun isDarkMode(resources: Resources): Boolean {
        val darkModeFlag = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return false
    }
}