package com.gyub.puumin.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.gyub.puumin.auth.ui.EMAIL_LOGIN_ROUTE
import com.gyub.puumin.auth.ui.LOGIN_HOME_ROUTE
import com.gyub.puumin.auth.ui.emailLoginScreen
import com.gyub.puumin.auth.ui.loginHomeScreen

/**
 * 로그인 네비게이션 그래프
 *
 * @author   Gyub
 * @created  2024/06/25
 */
@Composable
fun LoginNavHost(
    modifier: Modifier,
    navController: NavHostController,
    startDestination: String = LOGIN_HOME_ROUTE,
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = startDestination
    ) {
        loginHomeScreen { navController.navigate(EMAIL_LOGIN_ROUTE) }
        emailLoginScreen()
    }
}