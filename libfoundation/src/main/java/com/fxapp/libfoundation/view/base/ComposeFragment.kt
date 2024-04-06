package com.fxapp.libfoundation.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.fxapp.libfoundation.view.compose.ComposeObject

abstract class ComposeFragment : BaseFragment() {

    open val composeScreen: ComposeObject = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(inflater.context).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                composeScreen()
            }
        }
    }

    protected inline fun <reified T : NavArgs> getParentArgs(): T? {
        return parentFragment?.navArgs<T>()?.value
    }

}