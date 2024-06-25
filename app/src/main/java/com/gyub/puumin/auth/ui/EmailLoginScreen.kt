package com.gyub.puumin.auth.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.design.component.textfield.RoundedInputTextField
import com.gyub.puumin.MainActivity
import com.gyub.puumin.R
import com.gyub.puumin.auth.LoginViewModel
import com.gyub.puumin.base.state.UiState

/**
 * 이메일 로그인 화면
 *
 * @author   Gyub
 * @created  2024/06/25
 */
const val EMAIL_LOGIN_ROUTE = "EMAIL_LOGIN_ROUTE"

fun NavGraphBuilder.emailLoginScreen() {
    composable(EMAIL_LOGIN_ROUTE) {
        LoginRoute()
    }
}

@Composable
fun LoginRoute(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    EmailLoginScreen(
        email = email,
        password = password,
        loginUiState = loginUiState,
        updateEmail = viewModel::updateEmail,
        updatePassword = viewModel::updatePassword,
        login = viewModel::login
    )
}

@Composable
fun EmailLoginScreen(
    modifier: Modifier = Modifier,
    email: String,
    password: String,
    loginUiState: UiState,
    updateEmail: (String) -> Unit,
    updatePassword: (String) -> Unit,
    login: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        RoundedInputTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            value = email,
            onValueChange = updateEmail,
            placeholder = stringResource(id = R.string.input_email),
        )

        Spacer(modifier = modifier.height(10.dp))

        RoundedInputTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            value = password,
            onValueChange = updatePassword,
            placeholder = stringResource(id = R.string.input_password),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = modifier.height(10.dp))

        Button(
            onClick = login,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            enabled = email.isNotBlank() && password.isNotBlank(),
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp)
        ) {
            Text(text = stringResource(R.string.login))
        }

        LaunchedEffect(key1 = loginUiState) {
            if (loginUiState == UiState.Success) {
                showHomeActivity(context)
            }
        }
    }
}

/**
 * 홈 화면 이동
 *
 * @param context
 */
private fun showHomeActivity(context: Context) {
    context.startActivity(Intent(context, MainActivity::class.java))
}

@Preview(showBackground = true)
@Composable
fun EmailLoginScreenPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val updateEmail: (String) -> Unit = { email = it }
    val updatePassword: (String) -> Unit = { password = it }

    EmailLoginScreen(
        modifier = Modifier,
        email = email,
        password = password,
        updateEmail = updateEmail,
        updatePassword = updatePassword,
        loginUiState = UiState.Loading,
        login = {}
    )
}