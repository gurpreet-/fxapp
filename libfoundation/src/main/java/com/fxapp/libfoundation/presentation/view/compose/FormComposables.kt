package com.fxapp.libfoundation.presentation.view.compose

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.fxapp.libfoundation.presentation.view.theme.Colours
import com.fxapp.libfoundation.presentation.view.theme.Typography

@Composable
fun FormTextField(
    value: String,
    placeholder: String? = null,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions? = null,
    textStyle: TextStyle? = null,
    suffix: ComposeObject? = null,
    label: ComposeObject? = null,
    colours: TextFieldColors? = null,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier,
        label = label,
        keyboardOptions = keyboardOptions ?: KeyboardOptions.Default,
        value = value,
        textStyle = textStyle ?: Typography.default().bodyMedium,
        placeholder = placeholder?.run {
            {
                Text(
                    this,
                    color = Colours.default().secondary,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        onValueChange = onValueChange,
        suffix = suffix,
        colors = colours ?: Colours.defaultTextFieldColors()
    )
}

@Preview
@Composable
fun PreviewFormTextField() {
    FormTextField(
        "Hello", "0.00"
    ) {

    }
}