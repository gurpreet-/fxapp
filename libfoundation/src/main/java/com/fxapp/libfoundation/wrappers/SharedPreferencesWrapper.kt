package com.fxapp.libfoundation.wrappers

import android.content.SharedPreferences

interface SharedPreferencesWrapper {
    fun edit(
        commit: Boolean = false,
        action: SharedPreferences.Editor.() -> Unit
    )

    fun getBoolean(key: String, default: Boolean): Boolean

    companion object {
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
    }
}
