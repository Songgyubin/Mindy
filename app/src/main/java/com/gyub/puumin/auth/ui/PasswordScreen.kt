package com.gyub.puumin.auth.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.common.util.Validator.isValidPassword
import com.gyub.design.component.textfield.RoundedInputTextField
import com.gyub.design.theme.PuumInTypography
import com.gyub.puumin.R
import com.gyub.puumin.auth.SignUpViewModel
import com.gyub.puumin.base.state.UiState

/**
 * 비밀번호 입력 화면
 *
 * @author   Gyub
 * @created  2024/06/19
 */
const val PASSWORD_ROUTE = "PASSWORD_ROUTE"
fun NavGraphBuilder.passwordScreen() {
    composable(route = PASSWORD_ROUTE) {
        PasswordRoute()
    }
}

@Composable
fun PasswordRoute(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val password by viewModel.password.collectAsStateWithLifecycle()
    val passwordConfirm by viewModel.passwordConfirm.collectAsStateWithLifecycle()
    val registerUiState by viewModel.registerUiState.collectAsStateWithLifecycle()
    PasswordScreen(
        modifier,
        password,
        passwordConfirm,
        registerUiState,
        viewModel::updatePassword,
        viewModel::updatePasswordConfirm,
        viewModel::registerUiState
    )
}

@Composable
fun PasswordScreen(
    modifier: Modifier = Modifier,
    password: String,
    passwordConfirm: String,
    registerUiState: UiState,
    updatePassword: (String) -> Unit,
    updatePasswordConfirm: (String) -> Unit,
    register: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.please_input_password),
            style = PuumInTypography.headlineSmall,
        )
        Spacer(modifier = modifier.height(10.dp))

        RoundedInputTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            value = password,
            onValueChange = updatePassword,
            placeholder = stringResource(id = R.string.password),
            visualTransformation = PasswordVisualTransformation()
        )

        if (!isValidPassword(password)) {
            Text(
                text = stringResource(R.string.password_rule),
                style = PuumInTypography.labelSmall,
                color = Color.Red
            )
        }

        Text(
            text = stringResource(R.string.please_confirm_password),
            style = PuumInTypography.headlineSmall,
        )

        Spacer(modifier = modifier.height(10.dp))

        RoundedInputTextField(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            value = passwordConfirm,
            onValueChange = updatePasswordConfirm,
            placeholder = stringResource(id = R.string.confirm_password),
            visualTransformation = PasswordVisualTransformation()
        )

        if (passwordConfirm.isNotBlank() && password != passwordConfirm) {
            Text(
                text = stringResource(R.string.not_matched_password),
                style = PuumInTypography.labelSmall,
                color = Color.Red
            )
        }

        Button(
            onClick = register,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
            ),
            enabled = isValidPassword(password) && password == passwordConfirm,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp)
        ) {
            Text(text = stringResource(R.string.finish))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordScreenPreview() {
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var registerUiState by remember { mutableStateOf(UiState.Loading) }

    PasswordScreen(
        password = password,
        passwordConfirm = passwordConfirm,
        registerUiState = registerUiState,
        updatePassword = { password = it },
        updatePasswordConfirm = { passwordConfirm = it },
        register = {}
    )
}