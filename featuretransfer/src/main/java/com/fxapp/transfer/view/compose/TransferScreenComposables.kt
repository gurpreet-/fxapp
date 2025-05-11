package com.fxapp.transfer.view.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import com.fxapp.libfoundation.presentation.view.compose.FxAppScreen
import com.fxapp.libfoundation.presentation.view.compose.SpacerHeight
import com.fxapp.libfoundation.presentation.view.compose.SupportingText
import com.fxapp.libfoundation.presentation.view.compose.koinLocalViewModel
import com.fxapp.libfoundation.presentation.view.theme.Dimens.extraSmallMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.largeMargin
import com.fxapp.libfoundation.presentation.view.theme.Dimens.mediumMargin
import com.fxapp.transfer.R
import com.fxapp.transfer.viewmodel.TransferViewModel

@Composable
fun TransferMainScreenComposable(
    viewModel: TransferViewModel = koinLocalViewModel()
) = FxAppScreen {
    val formattedAmount = viewModel.getExchangedAmountFormatted()
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var accountNumber by remember { mutableStateOf(TextFieldValue("")) }
    var sortCode by remember { mutableStateOf(TextFieldValue("")) }
    var reference by remember { mutableStateOf(TextFieldValue("")) }
    val fillMaxWidth = Modifier.fillMaxWidth()
    Column(fillMaxWidth.verticalScroll(rememberScrollState()).padding(largeMargin)) {
        Text(stringResource(R.string.make_a_transfer, formattedAmount), style = MaterialTheme.typography.titleMedium)
        SpacerHeight(extraSmallMargin)
        Text(stringResource(R.string.make_a_transfer_description), style = MaterialTheme.typography.bodyMedium)
        SpacerHeight(mediumMargin)
        OutlinedTextField(
            name,
            modifier = fillMaxWidth,
            label = { Text(stringResource(R.string.name), style = MaterialTheme.typography.labelMedium) },
            onValueChange = { name = it }
        )
        SpacerHeight(extraSmallMargin)
        OutlinedTextField(
            accountNumber,
            modifier = fillMaxWidth,
            label = { Text(stringResource(R.string.account_number_label), style = MaterialTheme.typography.labelMedium) },
            supportingText = { SupportingText(stringResource(R.string.account_number_supporting_text)) },
            onValueChange = { accountNumber = it }
        )
        SpacerHeight(extraSmallMargin)
        OutlinedTextField(
            sortCode,
            modifier = fillMaxWidth,
            label = { Text(stringResource(R.string.sort_code), style = MaterialTheme.typography.labelMedium) },
            onValueChange = { sortCode = it }
        )
        SpacerHeight(extraSmallMargin)
        OutlinedTextField(
            reference,
            modifier = fillMaxWidth,
            label = { Text(stringResource(R.string.reference_label), style = MaterialTheme.typography.labelMedium) },
            placeholder = { Text(stringResource(R.string.reference_placeholder)) },
            supportingText = { SupportingText(stringResource(R.string.reference_supporting_text)) },
            onValueChange = { reference = it }
        )
    }
}