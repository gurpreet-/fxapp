package com.fxapp.view.fragment

import com.fxapp.libfoundation.view.base.ComposeFragment
import com.fxapp.libfoundation.view.compose.ComposeObject
import com.fxapp.view.compose.HomeScreen

class HomeFragment : ComposeFragment() {

    override val composeScreen: ComposeObject
        get() = { HomeScreen() }
}