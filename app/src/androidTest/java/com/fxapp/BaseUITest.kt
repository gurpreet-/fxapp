package com.fxapp

import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.mockk.clearAllMocks
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseUITest(
    kaspressoBuilder: Kaspresso.Builder = Kaspresso.Builder.withComposeSupport()
) : TestCase(kaspressoBuilder), KoinTest {

    open fun getTestingModule() = module {}

    @BeforeTest
    fun baseBeforeTest() {
        loadKoinModules(getTestingModule())
    }

    @AfterTest
    fun baseAfterTest() {
        clearAllMocks()
    }
}
