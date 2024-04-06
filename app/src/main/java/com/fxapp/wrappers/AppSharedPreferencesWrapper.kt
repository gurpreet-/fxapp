package com.fxapp.wrappers

import android.content.SharedPreferences
import androidx.core.content.edit
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper

class AppSharedPreferencesWrapper(
    val sharedPreferences: SharedPreferences
) : SharedPreferencesWrapper {
    override fun edit(commit: Boolean, action: SharedPreferences.Editor.() -> Unit) {
        sharedPreferences.edit(commit, action)
    }

    override fun getBoolean(key: String, default: Boolean) = sharedPreferences.getBoolean(key, default)
}