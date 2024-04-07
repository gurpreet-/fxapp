package com.fx.fxapp.libfoundation

import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.CurrencyMap
import com.fxapp.libfoundation.data.LatestRates
import com.fxapp.libfoundation.model.ConversionModel
import com.fxapp.libfoundation.repository.ApiRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ConversionModelTest : BaseUnitTest() {

    private val apiRepository = mockk<ApiRepository>(relaxed = true)
    private lateinit var conversionModel: ConversionModel

    val oneGbp = Amount("GBP", BigDecimal.ONE)
    val latestRates = LatestRates(
        1.0,
        "GBP",
        "2024-04-01",
        mapOf(
            "USD" to 1.2,
            "EUR" to 1.3,
            "GBP" to 1.0, // Purposefully add GBP
        )
    )

    @Before
    fun beforeTest() {
        coEvery { apiRepository.getLatestRates(oneGbp) } returns latestRates
        conversionModel = ConversionModel(apiRepository)
    }

    @Test
    fun `filters GBP out when requesting GBP`() = runTest {
        val rates = conversionModel.getExchangedRatesForAmountFormatted(oneGbp)
        assertFalse {
            rates.any { it.currencyCode == oneGbp.currency.currencyCode }
        }
    }

    @Test
    fun `adds currency code when formatting`() = runTest {
        val rates = conversionModel.getExchangedRatesForAmountFormatted(oneGbp)
        assertTrue {
            rates.all { it.formattedAmount.contains(CurrencyMap.map[it.currencyCode] ?: "not in") }
        }
    }

    @Test
    fun `adds amount when formatting`() = runTest {
        val rates = conversionModel.getExchangedRatesForAmountFormatted(oneGbp)
        assertTrue {
            rates.all { it.formattedAmount.contains(Regex("[0-9]")) }
        }
    }

    @Test
    fun `extracts number - test 1`() = runTest {
        val numberToTest = "$$22,.0"
        val expectedNumber = "22.00"
        testExtractsNumber(numberToTest, expectedNumber)
    }

    @Test
    fun `extracts number - test 2`() = runTest {
        val numberToTest = "9.992..0"
        val expectedNumber = "9.99"
        testExtractsNumber(numberToTest, expectedNumber)
    }

    @Test
    fun `extracts number - test 3`() = runTest {
        val numberToTest = "10.555."
        val expectedNumber = "10.55"
        testExtractsNumber(numberToTest, expectedNumber)
    }

    @Test
    fun `extracts number - test 4`() = runTest {
        val numberToTest = ".6*VVB~~>.00"
        val expectedNumber = "0.60"
        testExtractsNumber(numberToTest, expectedNumber)
    }

    @Test
    fun `extracts number - test 5`() = runTest {
        val numberToTest = "6"
        val expectedNumber = "6.00"
        testExtractsNumber(numberToTest, expectedNumber)
    }

    @Test
    fun `extracts number - test 6, contains two decimal places`() = runTest {
        val numberToTest = "234........."
        val expectedNumber = "234.00"
        val number = testExtractsNumber(numberToTest, expectedNumber)
        assertEquals(number.substringAfterLast(".").length, 2)
    }

    @Test
    fun `parsing malformed number in 0`() = runTest {
        val numberToTest = "234........."
        assertEquals("0", conversionModel.tryParseAsNumber(numberToTest))
    }

    private fun testExtractsNumber(toTest: String, expected: String): String {
        val onlyNumber = conversionModel.extractNumbersAndSeparator(toTest)
        assertEquals(expected, onlyNumber)
        return onlyNumber
    }
}
