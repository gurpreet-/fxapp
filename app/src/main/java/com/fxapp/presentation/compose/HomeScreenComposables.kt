package com.fxapp.presentation.compose

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
import androidx.compose.material3.SearchBarDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import com.fxapp.libfoundation.data.model.ConversionRepositoryImpl
import com.fxapp.libfoundation.domain.entities.Amount
import com.fxapp.libfoundation.domain.entities.AmountFormatted
import com.fxapp.libfoundation.presentation.event.FxAppEvent
import com.fxapp.libfoundation.presentation.view.base.showGlobalError
import com.fxapp.libfoundation.presentation.view.compose.ChevronRightIcon
import com.fxapp.libfoundation.presentation.view.compose.CircularLoading
import com.fxapp.libfoundation.presentation.view.compose.CurrencyItem
import com.fxapp.libfoundation.presentation.view.compose.CurrencyRatesListItem
import com.fxapp.libfoundation.presentation.view.compose.FormTextField
import com.fxapp.libfoundation.presentation.view.compose.FxAppBar
import com.fxapp.libfoundation.presentation.view.compose.FxAppScreen
import com.fxapp.libfoundation.presentation.view.compose.HorizontalDivider
import com.fxapp.libfoundation.presentation.view.compose.SimpleCallback
import com.fxapp.libfoundation.presentation.view.compose.SpacerHeight
import com.fxapp.libfoundation.presentation.view.compose.findNavController
import com.fxapp.libfoundation.presentation.view.compose.koinLocalViewModel
import com.fxapp.libfoundation.presentation.view.theme.Colours
import com.fxapp.libfoundation.presentation.view.theme.Dimens.defaultMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.extraSmallMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.largeIcon
import com.fxapp.libfoundation.presentation.view.theme.Dimens.largeMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.xLargeMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.xxLargeMargin
import com.fxapp.libfoundation.presentation.view.theme.Typography
import com.fxapp.presentation.compose.TestTags.AMOUNT_FIELD
import com.fxapp.presentation.compose.TestTags.CURRENCY_SELECTOR_BUTTON
import com.fxapp.presentation.compose.TestTags.CURRENCY_SELECTOR_SCREEN
import com.fxapp.presentation.fragment.HomeFragmentDirections
import com.fxapp.presentation.viewmodel.ConverterViewModel
import org.koin.compose.koinInject
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.Currency


@Composable
fun HomeScreen(
    converterViewModel: ConverterViewModel = koinLocalViewModel()
) = FxAppScreen {
    val navController = findNavController()
    val uiState by converterViewModel.uiState.collectAsStateWithLifecycle()
    val amount = uiState.amount

    LaunchedEffect(Unit) {
        converterViewModel.onEvent(FxAppEvent.GetAvailableCurrencies)
    }

    val context = LocalContext.current
    LaunchedEffect(uiState.error) {
        showGlobalError(context, uiState.error)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(Colours.default().viewBackground)
            .verticalScroll(rememberScrollState())
    ) {
        CurrencyExchangePanel {
            CurrencyTextField(
                decimalFormat = converterViewModel.getNumberFormat(amount.currency),
                value = amount.value,
                currency = amount.currency,
                availableCurrencies = uiState.availableCurrencies,
                onCurrencyChanged = {
                    converterViewModel.onEvent(FxAppEvent.SetAmount(Amount(it, amount.value)))
                },
                onValueChanged = {
                    converterViewModel.onEvent(FxAppEvent.SetAmount(Amount(amount.currency, it)))
                },
            )
        }

        if (uiState.isLoading) {
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
            if (amount.isZero()) {
                TypeSomething()
            } else {
                RatesList(uiState.formattedExchangeRates) {
                    navController.navigate(
                        HomeFragmentDirections.actionGlobalOpenTransferHub(
                            amount.value.toString(),
                            amount.currency.currencyCode,
                            it.value.toString(),
                            it.currency.currencyCode
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun RatesList(
    formattedExchangeRates: List<AmountFormatted>,
    conversionRepository: ConversionRepositoryImpl = koinInject(),
    onClick: (Amount) -> Unit
) {
    formattedExchangeRates.forEach { formattedAmount ->
        CurrencyRatesListItem(formattedAmount.currencyCode, formattedAmount.formattedAmount) {
            val converted = conversionRepository.extractNumbersAndSeparator(formattedAmount.formattedAmount)
            onClick(Amount(formattedAmount.currencyCode, BigDecimal(converted)))
        }
    }
}

@Composable
fun CurrencyExchangePanel(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    Modifier
        .shadow(4.dp)
        .background(Colours.default().slate10)
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
    conversionRepository: ConversionRepositoryImpl = koinInject(),
    onCurrencyChanged: (Currency) -> Unit,
    onValueChanged: (BigDecimal) -> Unit
) {
    val formatted = conversionRepository.format(currency, value)
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FormTextField(
            value = formatted,
            modifier = Modifier.weight(1f).testTag(AMOUNT_FIELD).focusRequester(focusRequester),
            placeholder = "0.00",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            textStyle = Typography.default()
                .bodyMedium
                .copy(color = Colours.default().green20, fontSize = 50.sp),
            colours = Colours.defaultTextFieldColors().copy(
                focusedIndicatorColor = Colours.default().transparent,
                unfocusedIndicatorColor = Colours.default().transparent,
                focusedTextColor = Colours.default().green20,
                unfocusedTextColor = Colours.default().green10,
                focusedContainerColor = Colours.default().transparent,
                unfocusedContainerColor = Colours.default().transparent,
                cursorColor = Colours.default().cursorColour,
            )
        ) { newString ->
            val newNumbersOnly = conversionRepository.extractNumbersAndSeparator(
                newString,
                decimalFormat.decimalFormatSymbols.decimalSeparator
            )
            val oldNumbersOnly = conversionRepository.extractNumbersAndSeparator(
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
        .testTag(AMOUNT_FIELD)
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
                currenciesToShow = if (searchText.isBlank()) availableCurrencies else searchedCurrencies,
                onSearched = { searchText = it },
                onCurrencyChanged = onCurrencyChanged,
                onClose = { showDialog = false },
            )
        }
    }

    if (availableCurrencies.isNotEmpty()) {
        FilledIconButton(
            modifier = modifier.testTag(CURRENCY_SELECTOR_BUTTON).requiredSizeIn(minWidth = 58.dp, maxHeight = 30.dp),
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
            .testTag(CURRENCY_SELECTOR_SCREEN)
            .fillMaxSize()
            .background(Colours.default().background)
    ) {
        FxAppBar(stringResource(R.string.select_currency), backPressed = onClose)
        SearchBar(
            searchText,
            placeholder = {
                Text(stringResource(R.string.search), color = Colours.default().slate50)
            },
            colors = SearchBarDefaults.colors(
                dividerColor = Colours.default().slate20
            ),
            trailingIcon = {
                Icon(
                    painterResource(R.drawable.clear),
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

object TestTags {
    const val AMOUNT_FIELD = "test_tag_amount_field"
    const val TYPE_SOMETHING = "test_tag_type_something"
    const val CURRENCY_SELECTOR_SCREEN = "test_tag_currency_selector_screen"
    const val CURRENCY_SELECTOR_BUTTON = "test_tag_currency_selector_button"
}