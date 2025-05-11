package com.fxapp.login.data.model

import com.fxapp.libfoundation.domain.repository.BaseRepository
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper.Companion.IS_LOGGED_IN
import com.fxapp.login.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val sharedPreferences: SharedPreferencesWrapper
) : BaseRepository(), AuthRepository {

    /**
     * Clear all shared preferences. This would normally
     * clear all singleton koin instances too.
     */
    override fun reset() {
        sharedPreferences.edit {
            it.clear()
        }
    }

    override var isLoggedIn: Boolean
        get() {
            // In production, this would be a network call checking
            // if an auth token has expired
            return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        }
        set(value) = sharedPreferences.edit {
            it.putBoolean(IS_LOGGED_IN, value)
        }

}