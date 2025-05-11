package com.fxapp.login.domain.repository

interface AuthRepository {
    var isLoggedIn: Boolean

    /**
     * Clear all shared preferences. This would normally
     * clear all singleton koin instances too.
     */
    fun reset()
}