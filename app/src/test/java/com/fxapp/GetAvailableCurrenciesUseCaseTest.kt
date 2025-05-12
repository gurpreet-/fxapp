package com.fxapp

import com.fxapp.domain.usecases.GetAvailableCurrenciesUseCase
import com.fxapp.libfoundation.domain.repository.ConversionRepository
import com.fxapp.libtest.BaseUnitTest
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GetAvailableCurrenciesUseCaseTest : BaseUnitTest() {

    private val conversionRepository = mockk<ConversionRepository>(relaxed = true)
    private lateinit var useCase: GetAvailableCurrenciesUseCase

    @Before
    fun beforeTest() {
        useCase = GetAvailableCurrenciesUseCase(conversionRepository,)
    }

    @Test
    fun `when invoked, then try login`() = runTest {
        useCase.invoke()
        verify {
            conversionRepository.getAvailableCurrencies()
        }
    }
}
