package com.gyub.puumin.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gyub.design.theme.PuumInTheme
import com.gyub.puumin.auth.ui.EMAIL_LOGIN_ROUTE
import com.gyub.puumin.auth.ui.LOGIN_HOME_ROUTE
import com.gyub.puumin.navigation.LoginDestination
import com.gyub.puumin.navigation.LoginNavHost
import com.gyub.puumin.ui.PuumInCenterAlignedAppBar
import dagger.hilt.android.AndroidEntryPoint

/**
 * 로그인 Activity
 *
 * @author   Gyub
 * @created  2024/06/25
 */
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuumInTheme {
                LoginScreen()
            }
        }
    }

    @Composable
    fun LoginScreen(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value
            ?.destination

        val currentScreen = when (currentDestination?.route) {
            LOGIN_HOME_ROUTE -> LoginDestination.LOGIN_HOME
            EMAIL_LOGIN_ROUTE -> LoginDestination.LOGIN_EMAIL
            else -> LoginDestination.LOGIN_HOME
        }

        Scaffold(
            topBar = {
                PuumInCenterAlignedAppBar(
                    title = currentScreen.title,
                    navigateUp = navController::navigateUp
                )
            },
        ) { contentPadding ->
            LoginNavHost(
                modifier = modifier.padding(contentPadding),
                navController = navController,
            )
        }
    }
}