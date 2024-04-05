package com.fxapp.libfoundation.viewmodel.base

import androidx.lifecycle.ViewModel
import com.fxapp.libfoundation.async.JobExecutor
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(), KoinComponent {

     private val jobExecutor by inject<JobExecutor>()

     fun launchOnIO(block: suspend CoroutineScope.() -> Unit)
          = jobExecutor.launchIO(block)

}