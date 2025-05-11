package com.fxapp.libfoundation.presentation.view.base

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = getColor(com.fxapp.libfoundation.R.color.colorPrimary),
                darkScrim = getColor(com.fxapp.libfoundation.R.color.colorPrimary),
            )
        )
        super.onCreate(savedInstanceState)
    }
}