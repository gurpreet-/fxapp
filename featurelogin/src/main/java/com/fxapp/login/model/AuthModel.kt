package com.fxapp.login.model

import com.fxapp.libfoundation.model.BaseModel
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper.Companion.IS_LOGGED_IN

class AuthModel(
    private val sharedPreferences: SharedPreferencesWrapper
) : BaseModel() {

    /**
     * Clear all shared preferences. This would normally
     * clear all singleton koin instances too.
     */
    fun reset() {
        sharedPreferences.edit {
            clear()
        }
    }

    var isLoggedIn: Boolean
        get() {
            // In production, this would be a network call checking
            // if an auth token has expired
            return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        }
        set(value) = sharedPreferences.edit {
            putBoolean(IS_LOGGED_IN, value)
        }

}