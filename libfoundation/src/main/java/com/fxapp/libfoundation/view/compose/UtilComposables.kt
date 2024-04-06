package com.fxapp.libfoundation.view.compose

import android.app.Activity
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import com.fxapp.libfoundation.R
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersDefinition

@Composable
fun findNavController() = findActivity().findNavController(R.id.main_fragment_container)

@Composable
fun findActivity(): Activity {
    var context = LocalContext.current
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Should be called in the context of an Activity")
}

@Composable
fun findActivityAsViewModelStoreOwner() = requireNotNull(findActivity() as? ViewModelStoreOwner)

@Composable
inline fun <reified T : ViewModel> koinLocalViewModel(
    viewModelStoreOwner: ViewModelStoreOwner = findActivityAsViewModelStoreOwner(),
    key: String? = null,
    noinline parametersDefinition: ParametersDefinition? = null
): T = koinViewModel<T>(viewModelStoreOwner = viewModelStoreOwner, key = key, parameters = parametersDefinition)