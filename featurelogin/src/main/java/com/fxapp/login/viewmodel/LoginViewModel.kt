package com.fxapp.login.viewmodel

import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper
import com.fxapp.libfoundation.wrappers.SharedPreferencesWrapper.Companion.IS_LOGGED_IN

class LoginViewModel(
    val sharedPreferences: SharedPreferencesWrapper
) : BaseViewModel() {

    fun isLoggedIn() = sharedPreferences.getBoolean(IS_LOGGED_IN, true)

}