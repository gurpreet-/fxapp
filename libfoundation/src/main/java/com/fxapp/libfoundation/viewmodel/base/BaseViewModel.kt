package com.fxapp.libfoundation.viewmodel.base

import androidx.lifecycle.ViewModel
import com.fxapp.libfoundation.async.JobExecutor
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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

     /**
      * Launch a coroutine on the IO thread
      * and add it to our job executor.
      */
     fun launchOnIO(block: suspend CoroutineScope.() -> Unit)
          = jobExecutor.launchIO(block)

}