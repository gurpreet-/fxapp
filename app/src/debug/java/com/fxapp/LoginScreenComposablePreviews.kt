package com.fxapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fxapp.login.view.compose.LoginScreen

@Preview
@Composable
private fun PreviewLoginScreen() {
    LoginScreen(
        "s",
        "password",
        true,
        {},
        {}
    ) {

    }
}