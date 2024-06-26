package com.gyub.puumin.auth.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.common.util.ToastUtil.showToast
import com.gyub.common.util.Validator.isValidEmail
import com.gyub.design.component.button.BlackWhiteBasicButton
import com.gyub.design.component.textfield.RoundedInputTextField
import com.gyub.design.theme.PuumInTypography
import com.gyub.puumin.R
import com.gyub.puumin.auth.SignUpViewModel
import com.gyub.puumin.base.state.UiState

/**
 * 이메일 인증 화면
 *
 * @author   Gyub
 * @created  2024/06/19
 */
const val EMAIL_AUTH_ROOT = "EMAIL_AUTH_ROOT"

fun NavGraphBuilder.emailAuthScreen(
    onNext: () -> Unit,
) {
    composable(EMAIL_AUTH_ROOT) {
        EmailAuthRoute(onNext = onNext)
    }
}

@Composable
fun EmailAuthRoute(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    onNext: () -> Unit,
) {
    val email by viewModel.email.collectAsStateWithLifecycle()
    val emailSendingUiState by viewModel.emailSendingUiState.collectAsStateWithLifecycle()
    val emailVerifyUiState by viewModel.emailVerifyUiState.collectAsStateWithLifecycle()
    val emailVerifyCode by viewModel.emailVerifyCode.collectAsStateWithLifecycle()

    EmailAuthScreen(
        modifier,
        email,
        emailVerifyCode,
        emailSendingUiState,
        emailVerifyUiState,
        viewModel::updateEmail,
        viewModel::sendEmailCode,
        viewModel::updateEmailVerifyCode,
        viewModel::verifyEmail,
        viewModel::resetEmailVerifyState,
        onNext
    )
}

@Composable
fun EmailAuthScreen(
    modifier: Modifier = Modifier,
    email: String,
    emailVerifyCode: String,
    emailSendingUiState: UiState,
    emailVerifyUiState: UiState,
    updateEmail: (String) -> Unit,
    sendEmailCode: () -> Unit,
    updateEmailVerifyCode: (String) -> Unit,
    verifyEmail: () -> Unit,
    resetEmailVerifyState: () -> Unit,
    onNext: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        EmailInput(
            email = email,
            updateEmail = updateEmail
        )

        when (emailSendingUiState) {
            UiState.Error, UiState.Loading -> {
                BlackWhiteBasicButton(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp),
                    onClick = sendEmailCode,
                    enabled = isValidEmail(email) && emailSendingUiState !is UiState.Success,
                    text = if (emailSendingUiState is UiState.Loading) stringResource(R.string.authentication_request)
                    else stringResource(R.string.retry)
                )
            }

            UiState.Success -> {
                Spacer(modifier = modifier.height(10.dp))
                RoundedInputTextField(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    value = emailVerifyCode,
                    placeholder = stringResource(R.string.input_verify_code),
                    onValueChange = updateEmailVerifyCode
                )
                BlackWhiteBasicButton(
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 15.dp),
                    onClick = verifyEmail,
                    enabled = email.isNotBlank() && emailVerifyCode.isNotBlank(),
                    text = stringResource(R.string.next)
                )
            }
        }

        LaunchedEffect(key1 = emailVerifyUiState) {
            handleEmailVerifyUiState(
                emailVerifyUiState,
                context,
                resetEmailVerifyState,
                onNext
            )
        }
    }
}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: String,
    updateEmail: (String) -> Unit,
) {
    Text(
        text = stringResource(R.string.please_input_email),
        style = PuumInTypography.headlineSmall,
    )
    Spacer(modifier = modifier.height(10.dp))
    RoundedInputTextField(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        value = email,
        placeholder = stringResource(R.string.example_email),
        onValueChange = updateEmail
    )
    Text(
        text = stringResource(R.string.if_complete_email_auth_can_next_step),
        style = PuumInTypography.bodySmall,
        color = Color.DarkGray
    )
}

@Composable
fun ColumnScope.RequestButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
        ),
        enabled = enabled,
        modifier = modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 15.dp)
    ) {
        Text(text = text)
    }
}

private fun handleEmailVerifyUiState(
    emailVerifyUiState: UiState,
    context: Context,
    resetEmailVerifyState: () -> Unit,
    onNext: () -> Unit,
) {
    when (emailVerifyUiState) {
        UiState.Error -> showToast(context, R.string.authentication_failed, isLong = false)
        UiState.Loading -> Unit
        UiState.Success -> {
            resetEmailVerifyState()
            onNext()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmailAuthScreenPreview() {
    var email by remember { mutableStateOf("") }
    var emailVerifyCode by remember { mutableStateOf("") }
    var emailSendingUiState by remember { mutableStateOf(UiState.Success) }
    var emailVerifyUiState by remember { mutableStateOf(UiState.Loading) }

    EmailAuthScreen(
        modifier = Modifier,
        email = email,
        emailVerifyCode = emailVerifyCode,
        emailSendingUiState = emailSendingUiState,
        emailVerifyUiState = emailVerifyUiState,
        updateEmail = { email = it },
        sendEmailCode = { },
        updateEmailVerifyCode = { emailVerifyCode = it },
        verifyEmail = {},
        resetEmailVerifyState = { },
        onNext = { },
    )
}