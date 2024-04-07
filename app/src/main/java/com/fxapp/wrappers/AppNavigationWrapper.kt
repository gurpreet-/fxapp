package com.fxapp.wrappers

import android.content.Context
import android.content.Intent
import androidx.navigation.NavDeepLinkBuilder
import com.fxapp.R
import com.fxapp.libfoundation.wrappers.NavigationWrapper
import com.fxapp.login.view.activity.LoginActivity


class AppNavigationWrapper(
    private val context: Context
) : NavigationWrapper {
    override val mainNavGraphId: Int
        get() = R.navigation.global_nav_graph

    /**
     * Simply navigates to a destination within the app
     * whilst opening a new instance of the app. Warning,
     * will fire MainActivity again so ensure this code does
     * not take you into a loop!
     */
    override fun navigateToDeepLink(destinationId: Int, withGraphId: Int?) {
        NavDeepLinkBuilder(context)
            .setGraph(withGraphId ?: mainNavGraphId)
            .setDestination(destinationId)
            .createPendingIntent()
            .send()
    }

    /**
     * As we want to clear all activities from the back
     * stack, we have to launch our LoginActivity with
     * flags to indicate that.
     */
    override fun logout() = context.run {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}