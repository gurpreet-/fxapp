package com.fxapp.wrappers

import android.content.Context
import androidx.navigation.NavDeepLinkBuilder
import com.fxapp.R
import com.fxapp.libfoundation.wrappers.NavigationWrapper

class AppNavigationWrapper(
    val context: Context
) : NavigationWrapper {
    override val mainNavGraphId: Int
        get() = R.navigation.global_nav_graph

    override fun navigateToDeepLink(destinationId: Int, withGraphId: Int?) {
        NavDeepLinkBuilder(context)
            .setGraph(withGraphId ?: mainNavGraphId)
            .setDestination(destinationId)
            .createPendingIntent()
            .send()
    }

}