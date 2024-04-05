package com.fxapp.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.view.compose.FormTextField
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.RenderPreview
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.extraSmallMargin
import com.fxapp.libfoundation.view.theme.Dimens.smallIcon
import com.fxapp.libfoundation.view.theme.Dimens.smallMargin
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
        CurrencyExchangePanel(
            Modifier
                .shadow(2.dp)
                .background(
                    Colours.default().primaryColourDark
                )
                .fillMaxWidth()
                .padding(horizontal = smallMargin, vertical = extraSmallMargin)
        )
        Column(
        ) {

        }
    }
}

@Composable
fun CurrencyExchangePanel(modifier: Modifier = Modifier) {
    var firstAmount by remember { mutableStateOf(BigDecimal.ZERO) }
    var secondAmount by remember { mutableStateOf(BigDecimal.ZERO) }
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CurrencyTextField(firstAmount) {
            firstAmount = it
        }
        CurrencyTextField(secondAmount) {
            secondAmount = it
        }
    }
}

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
        Spacer(Modifier.weight(0.42f))
        FilledIconButton(
            modifier = Modifier
                .requiredSizeIn(minWidth = 58.dp, maxHeight = 30.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {},
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Colours.default().cursorColour,
                contentColor = Colours.default().primaryColour
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "EUR",
                    style = Typography.default().bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(extraSmallMargin))
                Icon(
                    painterResource(R.drawable.chevron_down),
                    stringResource(R.string.ac_more),
                    modifier = Modifier.size(smallIcon),
                )
            }
        }
        FormTextField(
            value = formatted,
            placeholder = "0.00",
            modifier = Modifier.weight(1f),
            textStyle = Typography.default().titleLarge.copy(color = Colours.default().secondary),
            colours = Colours.defaultTextFieldColors().copy(
                focusedTextColor = Colours.default().formFieldTextColour.copy(alpha = 0.9f),
                unfocusedTextColor = Colours.default().formFieldTextColour.copy(alpha = 0.9f),
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
    }
}

private fun numbersOnly(s: String, decimalSeparator: Char): String {
    return s.filter {
        it.isDigit() || it == decimalSeparator
    }
}

@Preview
@Composable
private fun PreviewCurrencyExchangePanel() = RenderPreview {
    CurrencyExchangePanel(Modifier.fillMaxWidth())
}