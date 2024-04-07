package com.fxapp.login.model

import com.fxapp.libfoundation.model.BaseModel
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper.Companion.IS_LOGGED_IN

class AuthModel(
    val sharedPreferences: SharedPreferencesWrapper
) : BaseModel() {

    fun isLoggedIn(): Boolean {
        // This would be a network call checking
        // if an auth token has expired if this app was in production
        return sharedPreferences.getBoolean(IS_LOGGED_IN, true)
    }

}