package com.fxapp.libfoundation.view.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fxapp.libfoundation.R
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens
import com.fxapp.libfoundation.view.theme.Typography

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
fun FxFilledIconButton(modifier: Modifier = Modifier) {
    FilledIconButton(
        modifier = modifier
            .requiredSizeIn(minWidth = 58.dp, maxHeight = 30.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {},
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
                "EUR",
                style = Typography.default().bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.width(Dimens.extraSmallMargin))
            Icon(
                painterResource(R.drawable.chevron_down),
                stringResource(R.string.ac_more),
                modifier = Modifier.size(Dimens.smallIcon),
            )
        }
    }
}