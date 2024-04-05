package com.fxapp.libfoundation.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.mediumMargin
import com.fxapp.libfoundation.view.theme.Dimens.smallMargin
import java.util.Currency

@Composable
fun FullScreenSelector() {

}

@Composable
fun SpacerHeight(dimen: Dp) {
    Spacer(Modifier.height(dimen))
}

@Composable
fun SpacerWidth(dimen: Dp) {
    Spacer(Modifier.width(dimen))
}

@Composable
fun CurrencyItem(currency: Currency) = Row(
    Modifier.padding(smallMargin, mediumMargin),
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        currency.currencyCode,
        style = MaterialTheme.typography.bodyLarge,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.Bold,
    )
    SpacerWidth(smallMargin)
    Text(
        "(${currency.symbol})",
        style = MaterialTheme.typography.bodyMedium,
        color = Colours.default().slate50
    )
}

@Composable
fun HorizontalDivider() = androidx.compose.material3.HorizontalDivider(
    color = Colours.default().dividerColour
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FxAppBar(
    title: String = "",
    backPressed: SimpleCallback? = null
) = TopAppBar(
    title = {
        Text(
            title,
            Modifier.padding(horizontal = smallMargin),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            color = Colours.default().secondary
        )
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Colours.default().primaryColourDark,
        titleContentColor = Colours.default().secondary
    ),
    navigationIcon = {
        Icon(painterResource(androidx.appcompat.R.drawable.abc_ic_ab_back_material),
            tint = Colours.default().secondary,
            contentDescription = "back",
            modifier = Modifier
                .padding(smallMargin)
                .clickable {
                    backPressed?.invoke()
                })
    }
)