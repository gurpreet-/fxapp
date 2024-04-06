package com.fxapp.libfoundation.viewmodel.base

import androidx.lifecycle.ViewModel
import com.fxapp.libfoundation.async.JobExecutor
import com.fxapp.libfoundation.view.base.BaseUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

open class BaseViewModel : ViewModel(), KoinComponent {

     /**
      * Stores our in-progress Jobs.
      */
     private val jobExecutor by inject<JobExecutor>()

     override fun onCleared() {
          // When this view model is cleared out of
          // memory, cancel all our running jobs.
          jobExecutor.cancelAll()
     }

     fun <T : BaseUIState> showError(flow: MutableStateFlow<T>, e: Throwable) {
          Timber.e(e)
          flow.update {
               val new = it
               new.error = e
               new
          }
     }

     /**
      * Launch a coroutine on the IO thread
      * and add it to our job executor.
      */
     fun launchOnIO(block: suspend CoroutineScope.() -> Unit)
          = jobExecutor.launchIO(block)

}