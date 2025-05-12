package com.fxapp

import com.fxapp.domain.usecases.GetNumberFormatUseCase
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import com.fxapp.libtest.BaseUnitTest
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.util.Currency

class GetNumberFormatUseCaseTest : BaseUnitTest() {

    private val conversionRepository = mockk<ConversionRepository>(relaxed = true)
    private lateinit var useCase: GetNumberFormatUseCase

    @Before
    fun beforeTest() {
        useCase = GetNumberFormatUseCase(conversionRepository)
    }

    @Test
    fun `when invoked, then try login`() = runTest {
        val currency = Currency.getInstance("GBP")
        useCase.invoke(currency)
        coVerify {
            conversionRepository.getNumberFormat(currency)
        }
    }
}
