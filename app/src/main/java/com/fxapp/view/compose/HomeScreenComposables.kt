package com.fxapp.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.data.Amount
import com.fxapp.libfoundation.data.AmountFormatted
import com.fxapp.libfoundation.view.compose.ChevronRightIcon
import com.fxapp.libfoundation.view.compose.CircularLoading
import com.fxapp.libfoundation.view.compose.CurrencyItem
import com.fxapp.libfoundation.view.compose.FormTextField
import com.fxapp.libfoundation.view.compose.FxAppBar
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.HorizontalDivider
import com.fxapp.libfoundation.view.compose.SimpleCallback
import com.fxapp.libfoundation.view.compose.SpacerHeight
import com.fxapp.libfoundation.view.compose.SpacerWidth
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.defaultMargin
import com.fxapp.libfoundation.view.theme.Dimens.extraSmallMargin
import com.fxapp.libfoundation.view.theme.Dimens.largeIcon
import com.fxapp.libfoundation.view.theme.Dimens.largeMargin
import com.fxapp.libfoundation.view.theme.Dimens.mediumIcon
import com.fxapp.libfoundation.view.theme.Dimens.xLargeMargin
import com.fxapp.libfoundation.view.theme.Dimens.xxLargeMargin
import com.fxapp.libfoundation.view.theme.Typography
import com.fxapp.model.ConversionModel
import com.fxapp.viewmodel.CurrencyConverterViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Currency


@Composable
fun HomeScreen(
    viewModel: CurrencyConverterViewModel = koinViewModel()
) = FxAppScreen {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val amount = uiState.amount

    LaunchedEffect(true) {
        viewModel.getAvailableCurrencies()
    }

    LaunchedEffect(amount) {
        viewModel.getRates(amount)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Colours.default().green10)
            .verticalScroll(rememberScrollState())
    ) {
        CurrencyExchangePanel {
            CurrencyTextField(
                decimalFormat = viewModel.getNumberFormat(amount.currency),
                value = amount.value,
                currency = amount.currency,
                availableCurrencies = uiState.availableCurrencies,
                onCurrencyChanged = {
                    viewModel.setAmount(Amount(it, amount.value))
                },
                onValueChanged = {
                    viewModel.setAmount(Amount(amount.currency, it))
                }
            )
        }

        if (amount.value.compareTo(BigDecimal.ZERO) == 0) {
            TypeSomething()
        } else {
            RatesList(uiState.formattedExchangeRates)
        }
    }
}

@Composable
fun ColumnScope.RatesList(formattedExchangeRates: List<AmountFormatted>) {
    if (formattedExchangeRates.isEmpty()) {
        Row(
            Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularLoading()
        }
    } else {
        formattedExchangeRates.forEach {
            CurrencyRatesLItem(it.currencyCode, it.formattedAmount)
        }
    }
}

@Composable
fun CurrencyExchangePanel(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    Modifier
        .shadow(4.dp)
        .background(Colours.default().primaryColourDark)
        .fillMaxWidth()
        .padding(defaultMargin),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    content = content
)

@Composable
fun CurrencyTextField(
    value: BigDecimal,
    currency: Currency,
    decimalFormat: DecimalFormat,
    availableCurrencies: List<Currency> = listOf(),
    conversionModel: ConversionModel = koinInject(),
    onCurrencyChanged: (Currency) -> Unit,
    onValueChanged: (BigDecimal) -> Unit
) {
    val formatted = conversionModel.format(currency, value)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FormTextField(
            value = formatted,
            modifier = Modifier.weight(1f),
            placeholder = "0.00",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            textStyle = Typography.default()
                .bodyMedium
                .copy(color = Colours.default().secondary, fontSize = 50.sp),
            colours = Colours.defaultTextFieldColors().copy(
                focusedTextColor = Colours.default().secondary,
                unfocusedTextColor = Colours.default().secondary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = Colours.default().cursorColour,
            )
        ) { newString ->
            val newNumbersOnly = conversionModel.numbersOnly(
                newString,
                decimalFormat.decimalFormatSymbols.decimalSeparator
            )
            val oldNumbersOnly = conversionModel.numbersOnly(
                formatted,
                decimalFormat.decimalFormatSymbols.decimalSeparator
            )

            val bdOld = BigDecimal(oldNumbersOnly)
            val bdNew = BigDecimal(newNumbersOnly)

            val isDifferent = bdOld.compareTo(bdNew) != 0
            val is100TimesLarger = bdOld.multiply(BigDecimal(100)).compareTo(bdNew) == 0
            if (isDifferent && !is100TimesLarger) {
                onValueChanged(bdNew)
            }
        }
        CurrencySelectorButton(
            currency = currency,
            availableCurrencies = availableCurrencies,
            onCurrencyChanged = onCurrencyChanged
        )
    }
}

@Composable
fun TypeSomething() = Column(
    Modifier
        .fillMaxWidth()
        .alpha(0.66f)
        .padding(vertical = xLargeMargin, horizontal = largeMargin),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    SpacerHeight(xxLargeMargin + largeMargin)
    Icon(
        painterResource(R.drawable.currency_exchange),
        tint = Colours.default().primaryColourDark,
        contentDescription = stringResource(R.string.ac_currency_exchange),
        modifier = Modifier.size(largeIcon)
    )
    SpacerHeight(defaultMargin)
    Text(
        stringResource(R.string.start_typing),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
    SpacerHeight(xLargeMargin)
}

@Composable
private fun CurrencyRatesLItem(
    currencyCode: String,
    formattedRate: String,
) {
    Row(Modifier
        .fillMaxWidth()
        .clickable { }
        .padding(defaultMargin),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            currencyCode,
            style = MaterialTheme.typography.bodyMedium
        )
        SpacerWidth(defaultMargin)
        Text(
            formattedRate,
            style = MaterialTheme.typography.titleLarge,
            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
        )
        Spacer(Modifier.weight(1f))
        ChevronRightIcon(
            tint = Colours.default().black,
            size = mediumIcon
        )
    }
}

@Composable
fun CurrencySelectorButton(
    modifier: Modifier = Modifier,
    currency: Currency,
    availableCurrencies: List<Currency>,
    onCurrencyChanged: (Currency) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    val searchedCurrencies = remember { mutableStateListOf<Currency>() }

    LaunchedEffect(searchText) {
        availableCurrencies
            .filter { it.currencyCode.startsWith(searchText, true) }
            .also {
                searchedCurrencies.apply {
                    clear()
                    addAll(it)
                }
            }
    }

    if (showDialog) {
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { showDialog = false }
        ) {
            CurrencySelectorScreen(
                currenciesToShow =
                if (searchText.isBlank()) availableCurrencies
                else searchedCurrencies,
                onSearched = { searchText = it },
                onCurrencyChanged = onCurrencyChanged,
                onClose = { showDialog = false },
            )
        }
    }

    FilledIconButton(
        modifier = modifier.requiredSizeIn(minWidth = 58.dp, maxHeight = 30.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            showDialog = !showDialog
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Colours.default().secondaryContainer,
            contentColor = Colours.default().primaryColour
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                currency.currencyCode,
                style = Typography.default().bodyMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.width(extraSmallMargin))
            ChevronRightIcon()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencySelectorScreen(
    currenciesToShow: List<Currency>,
    onSearched: (String) -> Unit,
    onCurrencyChanged: (Currency) -> Unit,
    onClose: SimpleCallback
) {
    var searchText by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .background(Colours.default().viewBackground)
    ) {
        FxAppBar(stringResource(R.string.select_currency), backPressed = onClose)
        SearchBar(
            searchText,
            placeholder = {
                Text(stringResource(R.string.search), color = Colours.default().slate50)
            },
            trailingIcon = {
                Icon(
                    painterResource(androidx.appcompat.R.drawable.abc_ic_clear_material),
                    contentDescription = stringResource(R.string.ac_search),
                    tint = Colours.default().slate50,
                    modifier = Modifier.clickable {
                        searchText = ""
                    }
                )
            },
            onActiveChange = {},
            onSearch = {},
            active = true,
            onQueryChange = {
                searchText = it
                onSearched(it)
            }
        ) {
            LazyColumn {
                items(currenciesToShow) {
                    CurrencyItem(
                        Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                onCurrencyChanged(it)
                                onClose()
                            }), it
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}