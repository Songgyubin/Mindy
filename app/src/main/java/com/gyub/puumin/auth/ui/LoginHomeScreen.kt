package com.gyub.puumin.auth.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gyub.design.R.drawable
import com.gyub.design.theme.Kakao
import com.gyub.design.theme.Naver
import com.gyub.design.theme.PuumInTypography
import com.gyub.puumin.R
import com.gyub.puumin.auth.SignUpActivity

/**
 * 로그인 화면
 *
 * @author   Gyub
 * @created  2024/06/16
 */
const val LOGIN_HOME_ROUTE = "LOGIN_HOME_ROUTE"

fun NavGraphBuilder.loginHomeScreen(
    onEmailLogin: () -> Unit,
) {
    composable(LOGIN_HOME_ROUTE) {
        LoginHomeRoute(onEmailLogin = onEmailLogin)
    }
}

@Composable
fun LoginHomeRoute(
    modifier: Modifier = Modifier,
    onEmailLogin: () -> Unit,
) {
    LoginHomeScreen(
        modifier = modifier,
        onEmailLogin = onEmailLogin
    )
}

@Composable
fun LoginHomeScreen(
    modifier: Modifier = Modifier,
    onEmailLogin: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = modifier
                .height(168.dp)
        )
        Image(
            painter = painterResource(id = drawable.login_logo),
            contentScale = ContentScale.Crop,
            contentDescription = "logo"
        )
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            KakaoLogin()
            Spacer(modifier = Modifier.height(16.dp))
            NaverLogin()
            Spacer(modifier = Modifier.height(16.dp))
            IdLogin(
                onEmailLogin = onEmailLogin
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier
                .padding(10.dp)
                .clickable { moveSignUp(context) }
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = PuumInTypography.bodyMedium.copy(
                        textDecoration = TextDecoration.Underline
                    )
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
fun LoginButton(
    backgroundColor: Color,
    iconResId: Int?,
    contentDescription: String,
    buttonText: String,
    textColor: Color,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = backgroundColor)
            .fillMaxWidth(),
    ) {
        iconResId?.let {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = contentDescription,
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = buttonText,
            color = textColor
        )
    }
}

@Composable
fun IdLogin(
    onEmailLogin: () -> Unit,
) {
    LoginButton(
        backgroundColor = Color.Gray,
        iconResId = null,
        contentDescription = "",
        buttonText = stringResource(id = R.string.login_id),
        textColor = Color.White,
        onClick = onEmailLogin
    )
}

@Composable
fun NaverLogin() {
    LoginButton(
        backgroundColor = Naver,
        iconResId = drawable.ico_naver_18x18,
        contentDescription = stringResource(id = R.string.login_naver),
        buttonText = stringResource(id = R.string.login_naver),
        textColor = Color.White,
        onClick = {}
    )
}

@Composable
fun KakaoLogin() {
    LoginButton(
        backgroundColor = Kakao,
        iconResId = drawable.ico_kakao_18x18,
        contentDescription = stringResource(id = R.string.login_kakao),
        buttonText = stringResource(id = R.string.login_kakao),
        textColor = Color.Black,
        onClick = {}
    )
}

/**
 * 회원가입 화면 이동
 */
private fun moveSignUp(context:Context) {
    val intent = Intent(context, SignUpActivity::class.java)
    context.startActivity(intent)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    LoginHomeScreen(
        onEmailLogin = {}
    )
}