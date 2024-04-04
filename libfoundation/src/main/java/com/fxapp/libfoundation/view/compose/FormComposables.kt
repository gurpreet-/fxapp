package com.fxapp.libfoundation.view.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Typography

@Composable
fun FormTextField(
    value: String,
    placeholder: String,
    textStyle: TextStyle? = null,
    trailingIcon: ComposeObject? = null,
    colours: TextFieldColors? = null,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        textStyle = textStyle ?: Typography.default().bodyMedium,
        placeholder = {
            Text(
                placeholder,
                color = Colours.default().inversePrimary,
                style = MaterialTheme.typography.titleLarge
            )
        },
        onValueChange = onValueChange,
        leadingIcon = trailingIcon,
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