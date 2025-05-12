package com.fxapp.libtest

import com.fxapp.libfoundation.async.CoroutineScopes
import com.fxapp.libfoundation.async.JobExecutor
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

open class BaseUnitTest {

    val testDispatcher = StandardTestDispatcher()
    val testScope = TestScope(testDispatcher)

    private val testCoroutineScopes = object : CoroutineScopes {
        override val mainScope = testScope
        override val ioScope = testScope
    }

    private val mockJobExecutor: JobExecutor = JobExecutor(testCoroutineScopes)

    open fun getTestingModule() = module {}
    private fun getInternalTestingModule() = module {
        factory { mockJobExecutor }
    }

    @Before
    fun baseBeforeTest() {
        startKoin {
            modules(getInternalTestingModule(), getTestingModule())
        }
    }

    @After
    fun baseAfterTest() {
        stopKoin()
    }

    protected fun runTest(
        context: CoroutineContext = EmptyCoroutineContext,
        timeout: Duration = 10.seconds,
        body: suspend TestScope.() -> Unit
    ) = kotlinx.coroutines.test.runTest(testDispatcher, timeout, body)
}
