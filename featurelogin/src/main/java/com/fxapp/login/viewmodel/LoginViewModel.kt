package com.fxapp.login.viewmodel

import com.fxapp.libfoundation.viewmodel.base.BaseViewModel
import com.fxapp.login.model.AuthModel

class LoginViewModel(
    val authModel: AuthModel
) : BaseViewModel() {

    fun isLoggedIn() = authModel.isLoggedIn()

}