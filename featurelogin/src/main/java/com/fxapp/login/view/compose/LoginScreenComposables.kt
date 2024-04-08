package com.fxapp.login.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.fxapp.libfoundation.view.compose.AppNameText
import com.fxapp.libfoundation.view.compose.FxAppScreen
import com.fxapp.libfoundation.view.compose.SimpleCallback
import com.fxapp.libfoundation.view.compose.SpacerHeight
import com.fxapp.libfoundation.view.compose.koinLocalViewModel
import com.fxapp.libfoundation.view.theme.Colours
import com.fxapp.libfoundation.view.theme.Dimens.defaultMargin
import com.fxapp.libfoundation.view.theme.Dimens.largeMargin
import com.fxapp.libfoundation.view.theme.Dimens.smallMargin
import com.fxapp.libfoundation.view.theme.Dimens.xxLargeMargin
import com.fxapp.login.R
import com.fxapp.login.viewmodel.LoginViewModel

@Composable
fun LoginMainScreenComposable(
    viewModel: LoginViewModel = koinLocalViewModel()
) = FxAppScreen {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val showHelperText by remember { mutableStateOf(viewModel.showHelperText()) }
    LoginScreen(username, password, showHelperText, { username = it }, { password = it}) {
        viewModel.login()
    }
}

@Composable
fun LoginScreen(
    userName: String,
    password: String,
    showHelperText: Boolean = false,
    onUserNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: SimpleCallback
) {
    Column(Modifier
        .background(Colours.default().green10)
        .fillMaxSize()
        .padding(horizontal = largeMargin)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        SpacerHeight(xxLargeMargin)
        AppNameText(
            Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
            colour = Colours.default().primaryColour
        )
        SpacerHeight(defaultMargin)
        if (showHelperText) {
            Text(stringResource(R.string.login_helper_text))
            SpacerHeight(defaultMargin)
        }
        OutlinedTextField(
            userName,
            label = { Text(stringResource(R.string.label_username), style = MaterialTheme.typography.labelMedium) },
            onValueChange = onUserNameChanged
        )
        SpacerHeight(smallMargin)
        OutlinedTextField(
            password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(stringResource(R.string.label_password), style = MaterialTheme.typography.labelMedium) },
            onValueChange = onPasswordChanged
        )
        SpacerHeight(defaultMargin)
        val login = stringResource(R.string.login)
        Button(onClick = onLoginClicked, modifier = Modifier.testTag(login)) {
            Text(
                login,
                color = Colours.default().secondary,
                fontWeight = FontWeight.Bold
            )
        }
        SpacerHeight(xxLargeMargin)
    }
}