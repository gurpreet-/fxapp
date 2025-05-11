package com.fxapp.libfoundation.presentation.view.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.fxapp.libfoundation.R

abstract class BaseFragment : Fragment() {

    val navController: NavController?
        get() = activity?.findNavController(R.id.main_fragment_container)

}
