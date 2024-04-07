package com.fxapp.libfoundation.view.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

abstract class BaseFragment : Fragment() {
    val navController: NavController? = null
}