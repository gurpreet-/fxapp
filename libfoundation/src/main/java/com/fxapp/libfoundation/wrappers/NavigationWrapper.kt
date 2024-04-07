package com.fxapp.libfoundation.wrappers

import androidx.annotation.NavigationRes

interface NavigationWrapper {
    val mainNavGraphId: Int
    fun navigateToDeepLink(destinationId: Int, @NavigationRes withGraphId: Int? = null)
}