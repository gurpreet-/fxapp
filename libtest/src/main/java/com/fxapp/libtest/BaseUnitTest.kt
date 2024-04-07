package com.fxapp.libtest

import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

open class BaseUnitTest {

    open fun getTestingModule() = module {  }

    @Before
    fun baseBeforeTest() {
        startKoin {
            modules(getTestingModule())
        }
    }

    @After
    fun baseAfterTest() {
        stopKoin()
    }

}