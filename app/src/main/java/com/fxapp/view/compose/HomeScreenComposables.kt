package com.fxapp.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.view.compose.FormTextField
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.FxFilledIconButton
import com.fxapp.libfoundation.view.compose.RenderPreview
import com.fxapp.libfoundation.view.compose.SpacerHeight
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.defaultIcon
import com.fxapp.libfoundation.view.theme.Dimens.defaultMargin
import com.fxapp.libfoundation.view.theme.Dimens.largeMargin
import com.fxapp.libfoundation.view.theme.Dimens.xLargeMargin
import com.fxapp.libfoundation.view.theme.Dimens.xxLargeMargin
import com.fxapp.libfoundation.view.theme.Typography
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.Currency
import java.util.Locale


@Composable
fun HomeScreen() = FxAppScreen {
    Column(
        Modifier
            .fillMaxSize()
            .background(Colours.default().green10)
            .verticalScroll(rememberScrollState())
    ) {
        var amount by remember { mutableStateOf(BigDecimal.ZERO) }
        CurrencyExchangePanel {
            CurrencyTextField(value = amount) {
                amount = it
            }
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            TypeSomething()
        }

    }
}

@Composable
fun CurrencyExchangePanel(
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    Modifier
        .shadow(4.dp)
        .background(
            Colours.default().primaryColourDark
        )
        .fillMaxWidth()
        .padding(defaultMargin),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    content = content
)

@Composable
fun CurrencyTextField(
    value: BigDecimal,
    onValueChange: (BigDecimal) -> Unit
) {
    val numberFormat = (DecimalFormat.getCurrencyInstance(Locale.UK) as DecimalFormat).apply {
        isParseBigDecimal = true
        roundingMode = RoundingMode.DOWN
        currency = Currency.getInstance("GBP")
        decimalFormatSymbols = decimalFormatSymbols.apply {
            decimalSeparator = '.'
            groupingSeparator = ','
        }
    }
    val formatted = numberFormat.format(value)
    val currentNumbersOnly by remember {
        mutableStateOf("")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FormTextField(
            value = formatted,
            modifier = Modifier.weight(1f),
            placeholder = "0.00",
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
        ) { formattedString ->
            val newNumbersOnly = numbersOnly(formattedString, numberFormat.decimalFormatSymbols.decimalSeparator)
            if (currentNumbersOnly != newNumbersOnly) {
                onValueChange(BigDecimal(newNumbersOnly))
            }
        }
        FxFilledIconButton()
    }
}

private fun numbersOnly(s: String, decimalSeparator: Char): String {
    return s.filter {
        it.isDigit() || it == decimalSeparator
    }
}

@Composable
private fun TypeSomething() = Column(
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
        modifier = Modifier.size(defaultIcon)
    )
    SpacerHeight(defaultMargin)
    Text(
        stringResource(R.string.start_typing),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge
    )
    SpacerHeight(xLargeMargin)
}

@Preview
@Composable
private fun PreviewCurrencyExchangePanel() = RenderPreview {
    CurrencyExchangePanel {
        CurrencyTextField(value = BigDecimal.ONE) {

        }
    }
}